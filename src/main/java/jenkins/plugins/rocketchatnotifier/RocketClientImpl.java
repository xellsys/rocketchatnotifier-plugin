package jenkins.plugins.rocketchatnotifier;

import jenkins.plugins.rocketchatnotifier.rocket.RocketChatClient;
import jenkins.plugins.rocketchatnotifier.rocket.RocketChatClientImpl;
import sun.security.validator.ValidatorException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Concrete RocketChat client implementation (@see {@link RocketChatClient})
 *
 * @author Martin Reinhardt (hypery2k)
 */
public class RocketClientImpl implements RocketClient {

  private static final Logger LOGGER = Logger.getLogger(RocketClientImpl.class.getName());

  public static final String API_PATH = "/api/";

  private RocketChatClient client;

  private String channel;

  public RocketClientImpl(String serverUrl, String user, String password, String channel) throws IOException {
    this.client = new RocketChatClientImpl(serverUrl, user, password);
    this.client.getChannels();
    this.channel = channel;
  }

  public boolean publish(String message) {
    try {
      LOGGER.fine("Starting sending message to channel " + this.channel);
      this.client.send(this.channel, message);
      return true;
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "I/O error error during publishing message", e);
      return false;
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Unknown error error during publishing message", e);
      return false;
    }
  }

  @Override
  public boolean publish(final String message, final String emoji, final String avatar) {
    try {
      LOGGER.fine("Starting sending message to channel " + this.channel);
      this.client.send(this.channel, message, emoji, avatar);
      return true;
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "I/O error error during publishing message", e);
      return false;
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Unknown error error during publishing message", e);
      return false;
    }
  }

  @Override
  public boolean publish(final String message, final String emoji, final String avatar, final List<Map<String, Object>> attachments) {
    try {
      LOGGER.fine("Starting sending message to channel " + this.channel);
      this.client.send(this.channel, message, emoji, avatar, attachments);
      return true;
    } catch (IOException e) {
      LOGGER.log(Level.SEVERE, "I/O error error during publishing message", e);
      return false;
    } catch (Exception e) {
      LOGGER.log(Level.SEVERE, "Unknown error error during publishing message", e);
      return false;
    }
  }

  @Override
  public void validate() throws ValidatorException, IOException {
    LOGGER.fine("Starting validating");
    this.client.getChannels();
  }

}


