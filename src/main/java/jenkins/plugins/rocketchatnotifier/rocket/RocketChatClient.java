package jenkins.plugins.rocketchatnotifier.rocket;

import jenkins.plugins.rocketchatnotifier.model.Room;
import jenkins.plugins.rocketchatnotifier.model.User;
import sun.security.validator.ValidatorException;

import java.io.IOException;

/**
 * API used by this Jenkins plugin to communicate to RocketChat server backend
 *
 * @author Martin Reinhardt (hypery2k)
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
   * @return an array of channels as room object
   * @throws IOException in case of communication errors with the RocketChat server backend
   */
  Room[] getChannels() throws IOException;

  /**
   * sends a message to a channel
   *
   * @param room    to use (aka channel)
   * @param message to send
   * @throws ValidatorException in case of SSL errors
   * @throws IOException        in case of communication errors with the RocketChat server backend
   */
  void send(Room room, String message) throws ValidatorException, IOException;

  /**
   * sends a message to a channel
   *
   * @param channelName to use
   * @param message     to send
   * @throws ValidatorException in case of SSL errors
   * @throws IOException        in case of communication errors with the RocketChat server backend
   */
  void send(String channelName, String message) throws ValidatorException, IOException;

}
