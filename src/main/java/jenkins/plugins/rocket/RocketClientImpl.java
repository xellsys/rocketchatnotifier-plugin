package jenkins.plugins.rocket;


import com.github.baloise.rocketchatrestclient.RocketChatClient;

import java.io.IOException;

/**
 * Created by mreinhardt on 08.09.16.
 */
public class RocketClientImpl implements RocketClient {

  private RocketChatClient client;

  private String channel;


  public RocketClientImpl(String serverUrl, String user, String password, String channel) {

    String newUrl = serverUrl.replace("https://", "https://" + user + ":" + password + '@').replace("http://", "http://" + user + ":" + password + '@');
    System.out.println(newUrl);
    this.client = new RocketChatClient(newUrl, user, password);
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
