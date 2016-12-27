package jenkins.plugins.rocketchatnotifier.rocket;

import jenkins.plugins.rocketchatnotifier.model.Room;
import jenkins.plugins.rocketchatnotifier.model.User;
import sun.security.validator.ValidatorException;

import java.io.IOException;

/**
 *
 */
public interface RocketChatClient {
  /**
   * Gets <strong>all</strong> of the users from a Rocket.Chat server, if you have a ton this will
   * take some time.
   *
   * @return an array of {@link User}s
   * @throws IOException is thrown if there was a problem connecting, including if the result
   *                     wasn't successful
   */
  User[] getUsers() throws IOException;

  /**
   * Retrieves a {@link User} from the Rocket.Chat server.
   *
   * @param userId of the user to retrieve
   * @return an instance of the {@link User}
   * @throws IOException is thrown if there was a problem connecting, including if the result
   *                     wasn't successful or there is no user
   */
  User getUser(String userId) throws IOException;

  /**
   * @return
   * @throws IOException
   */
  Room[] getChannels() throws IOException;

  void send(Room room, String message) throws ValidatorException, IOException;

  /**
   *
   * @param channelName
   * @param message
   * @throws ValidatorException
   * @throws IOException
   */
  void send(String channelName, String message) throws ValidatorException, IOException;

}
