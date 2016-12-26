package jenkins.plugins.rocketchatnotifier;


import jenkins.plugins.rocketchatnotifier.rocket.RocketChatClient;
import jenkins.plugins.rocketchatnotifier.rocket.RocketChatClientImpl;
import sun.security.validator.ValidatorException;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by mreinhardt on 08.09.16.
 */
public class RocketClientImpl implements RocketClient {

  private static final Logger LOG = Logger.getLogger(RocketClientImpl.class.getName());

  public static final String API_PATH = "/api/";

  private RocketChatClient client;

  private String channel;


  public RocketClientImpl(String serverUrl, String user, String password, String channel) {
    this.client = new RocketChatClientImpl(serverUrl + API_PATH, user, password);
    this.channel = channel;
  }

  public boolean publish(String message) {
    try {
      LOG.fine("Starting sending message to channel " + this.channel);
      this.client.send(this.channel, message);
      return true;
    } catch (ValidatorException e) {
      return false;
    } catch (IOException e) {
      return false;
    }
  }
}


