package jenkins.plugins.rocket;


import com.github.baloise.rocketchatrestclient.RocketChatClient;

import java.io.IOException;

/**
 * Created by mreinhardt on 08.09.16.
 */
public class RocketClientImpl implements RocketClient {

  public static final String API_PATH = "/api/";

  private RocketChatClient client;

  private String channel;


  public RocketClientImpl(String serverUrl, String user, String password, String channel) {
    this.client = new RocketChatClient(serverUrl + API_PATH, user, password);
    this.channel = channel;
  }

  public boolean publish(String message) {
    try {
      this.client.send(this.channel, message);
      return true;
    } catch (IOException e) {
      return false;
    }
  }
}
