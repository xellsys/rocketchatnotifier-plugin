package jenkins.plugins.rocketchatnotifier.rocket;

import jenkins.plugins.rocketchatnotifier.model.Room;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by mreinhardt on 26.12.16.
 */
public class RocketChatClientIT {

  private RocketChatClient client;

  @Before
  public void setup() throws Exception {
    String rocketVersionString = System.getProperty("rocket.version", "0.48.2");
    int rocketVersion = Integer.parseInt(rocketVersionString.split("\\.")[1]);
    if (rocketVersion >= 48) {
      this.client = new RocketChatClientImpl("http://localhost:4443/api/", "admin", "supersecret"); // TODO read from env
    } else {
      this.client = new LegacyRocketChatClient("http://localhost:4443/api/", "admin", "supersecret");
    }
  }

  @Test
  public void shouldReadRooms() throws Exception {
    Room[] rooms = this.client.getChannels();
    assertThat(rooms.length, is(1));
  }

  @Test
  public void shouldSendMessage() throws Exception {
    this.client.send("general", "test");
  }


}
