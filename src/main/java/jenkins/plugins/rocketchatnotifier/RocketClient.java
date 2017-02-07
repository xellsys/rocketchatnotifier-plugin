package jenkins.plugins.rocketchatnotifier;

import sun.security.validator.ValidatorException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by mreinhardt on 08.09.16.
 */
public interface RocketClient {

  boolean publish(String message);

  boolean publish(String message, String emoji, String avatar);

  boolean publish(String message, String emoji, String avatar, List<Map<String, Object>> attachments);

  void validate() throws ValidatorException, IOException;
}
