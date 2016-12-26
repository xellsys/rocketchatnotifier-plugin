package jenkins.plugins.rocketchatnotifier.rocket;

import jenkins.plugins.rocketchatnotifier.model.Room;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * Created by mreinhardt on 26.12.16.
 */
  public class RocketChatClientIT {

  private RocketChatClient client;

  @Before
  public void setup() throws Exception {
    this.client = new RocketChatClient("http://localhost:4443/api/v1/", "admin", "supersecret"); // TODO read from env
    this.client.login();
  }

  @After
  public void cleanUp() throws Exception {
    this.client.logout();
  }

  @Test
  public void shouldReadRooms() throws Exception {
    Set<Room> rooms = this.client.getPublicRooms();
    assertThat(rooms, hasSize(0));
  }
}
