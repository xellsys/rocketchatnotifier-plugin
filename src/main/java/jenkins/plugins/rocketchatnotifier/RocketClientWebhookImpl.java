package jenkins.plugins.rocketchatnotifier;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import jenkins.plugins.rocketchatnotifier.rocket.RocketChatClient;
import jenkins.plugins.rocketchatnotifier.rocket.RocketChatClientImpl;
import sun.security.validator.ValidatorException;

public class RocketClientWebhookImpl implements RocketClient {

  private static final Logger LOGGER = Logger.getLogger(RocketClientImpl.class.getName());

  private RocketChatClient client;

  public RocketClientWebhookImpl(String serverUrl, boolean trustSSL, String token) {
    client = new RocketChatClientImpl(serverUrl, trustSSL, token);
  }

  public boolean publish(String message) {
    try {
      LOGGER.fine("Starting sending message to webhook");
      this.client.send("", message);
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
      LOGGER.fine("Starting sending message to webhook");
      this.client.send("", message, emoji, avatar);
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
      LOGGER.fine("Starting sending message to webhook");
      this.client.send("", message, emoji, avatar, attachments);
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
    this.client.send("", "Test message from Jenkins via Webhook");
  }
}
