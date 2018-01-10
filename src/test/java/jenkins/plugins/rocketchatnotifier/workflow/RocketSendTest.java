package jenkins.plugins.rocketchatnotifier.workflow;

import hudson.model.Run;
import hudson.model.TaskListener;
import jenkins.model.Jenkins;
import jenkins.plugins.rocketchatnotifier.RocketChatNotifier;
import jenkins.plugins.rocketchatnotifier.RocketClientImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.PrintStream;

import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.whenNew;


@RunWith(PowerMockRunner.class)
@PrepareForTest({Jenkins.class, RocketSendStep.class, RocketClientImpl.class})
public class RocketSendTest {

  @Mock
  Jenkins jenkins;

  @Mock
  RocketChatNotifier.DescriptorImpl rocketDescMock;

  @Mock
  TaskListener taskListenerMock;

  @Mock
  PrintStream printStreamMock;

  @Mock
  Run run;

  @Mock
  RocketClientImpl rocketClientMock;

  @Before
  public void setUp() throws Exception {
    PowerMockito.mockStatic(Jenkins.class);
    whenNew(RocketClientImpl.class).withAnyArguments().thenReturn(rocketClientMock);
    when(jenkins.getDescriptorByType(RocketChatNotifier.DescriptorImpl.class)).thenReturn(rocketDescMock);
    when(rocketDescMock.getRocketServerUrl()).thenReturn("rocket.test.com");
    when(rocketDescMock.getUsername()).thenReturn("user");
    when(rocketDescMock.getPassword()).thenReturn("pass");
    when(rocketDescMock.getChannel()).thenReturn("default");
  }

  @Test
  public void shouldWorkWithDefaults() throws Exception {
    // given
    RocketSendStep.RocketSendStepExecution stepExecution = spy(new RocketSendStep.RocketSendStepExecution());
    RocketSendStep rocketSendStep = new RocketSendStep("message");
    stepExecution.step = rocketSendStep;
    stepExecution.listener = taskListenerMock;
    stepExecution.run = run;
    when(Jenkins.getInstance()).thenReturn(jenkins);
    // when
    when(taskListenerMock.getLogger()).thenReturn(printStreamMock);
    when(stepExecution.getRocketClient(anyString(), anyBoolean(), anyString(), anyString(), anyString())).thenReturn(rocketClientMock);
    stepExecution.run();
    // then
    verify(stepExecution, times(1)).getRocketClient("rocket.test.com", false, "user", "pass", "default");
  }

  @Test
  public void shouldOverrideDefaults() throws Exception {
    // given
    RocketSendStep.RocketSendStepExecution stepExecution = spy(new RocketSendStep.RocketSendStepExecution());
    RocketSendStep rocketSendStep = new RocketSendStep("message");
    rocketSendStep.setChannel("channel");
    stepExecution.step = rocketSendStep;
    stepExecution.listener = taskListenerMock;
    stepExecution.run = run;
    when(Jenkins.getInstance()).thenReturn(jenkins);
    // when
    when(taskListenerMock.getLogger()).thenReturn(printStreamMock);
    when(stepExecution.getRocketClient(anyString(), anyBoolean(), anyString(), anyString(), anyString())).thenReturn(rocketClientMock);
    stepExecution.run();
    // then
    verify(stepExecution, times(1)).getRocketClient("rocket.test.com", false, "user", "pass", "channel");
  }


}
