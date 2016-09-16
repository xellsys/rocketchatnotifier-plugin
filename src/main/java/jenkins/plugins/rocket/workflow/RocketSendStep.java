package jenkins.plugins.rocket.workflow;

import hudson.AbortException;
import hudson.Extension;
import hudson.Util;
import hudson.model.Run;
import hudson.model.TaskListener;
import jenkins.model.Jenkins;
import jenkins.plugins.rocket.Messages;
import jenkins.plugins.rocket.RocketChatNotifier;
import jenkins.plugins.rocket.RocketClient;
import jenkins.plugins.rocket.RocketClientImpl;
import org.jenkinsci.plugins.workflow.steps.AbstractStepDescriptorImpl;
import org.jenkinsci.plugins.workflow.steps.AbstractStepImpl;
import org.jenkinsci.plugins.workflow.steps.AbstractSynchronousNonBlockingStepExecution;
import org.jenkinsci.plugins.workflow.steps.StepContextParameter;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import javax.annotation.Nonnull;
import javax.inject.Inject;


/**
 * Workflow step to send a rocket channel notification.
 */
public class RocketSendStep extends AbstractStepImpl {
  private final
  @Nonnull
  String message;
  private String channel;
  private boolean failOnError;

  @Nonnull
  public String getMessage() {
    return message;
  }

  public String getChannel() {
    return channel;
  }

  @DataBoundSetter
  public void setChannel(String channel) {
    this.channel = Util.fixEmpty(channel);
  }

  public boolean isFailOnError() {
    return failOnError;
  }

  @DataBoundSetter
  public void setFailOnError(boolean failOnError) {
    this.failOnError = failOnError;
  }

  @DataBoundConstructor
  public RocketSendStep(@Nonnull String message) {
    this.message = message;
  }


  @Extension
  public static class DescriptorImpl extends AbstractStepDescriptorImpl {

    public DescriptorImpl() {
      super(RocketSendStepExecution.class);
    }

    @Override
    public String getFunctionName() {
      return "rocketSend";
    }

    @Override
    public String getDisplayName() {
      return Messages.RocketSendStepDisplayName();
    }
  }

  public static class RocketSendStepExecution extends AbstractSynchronousNonBlockingStepExecution<Void> {

    private static final long serialVersionUID = 1L;

    @Inject
    transient RocketSendStep step;

    @StepContextParameter
    transient TaskListener listener;

    @StepContextParameter
    transient Run run;

    @Override
    protected Void run() throws Exception {

      //default to global config values if not set in step, but allow step to override all global settings
      Jenkins jenkins;
      //Jenkins.getInstance() may return null, no message sent in that case
      try {
        jenkins = Jenkins.getInstance();
      } catch (NullPointerException ne) {
        listener.error(Messages.NotificationFailedWithException(ne));
        return null;
      }
      RocketChatNotifier.DescriptorImpl rocketDesc = jenkins.getDescriptorByType(RocketChatNotifier.DescriptorImpl.class);
      String server = rocketDesc.getRocketServerURL();
      String user = rocketDesc.getUsername();
      String password = rocketDesc.getPassword();
      String channel = step.channel != null ? step.channel : rocketDesc.getChannel();
      String jenkinsUrl = rocketDesc.getBuildServerUrl();
      // placing in console log to simplify testing of retrieving values from global config or from step field; also used for tests
      listener.getLogger().println(Messages.RocketSendStepConfig(channel, step.message));

      RocketClient rocketClient = getRocketClient(server, user, password, channel);

      String msgWithJobLink = step.message
        + "," + run.getDisplayName()
        + "," + jenkinsUrl + run.getUrl() + "";
      boolean publishSuccess = rocketClient.publish(msgWithJobLink);
      if (!publishSuccess && step.failOnError) {
        throw new AbortException(Messages.NotificationFailed());
      } else if (!publishSuccess) {
        listener.error(Messages.NotificationFailed());
      }
      return null;
    }

    //streamline unit testing
    RocketClient getRocketClient(String server, String user, String password, String channel) {
      return new RocketClientImpl(server, user, password, channel);
    }

  }
}
