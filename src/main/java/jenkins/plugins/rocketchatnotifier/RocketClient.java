package jenkins.plugins.rocketchatnotifier;

import jenkins.plugins.rocketchatnotifier.rocket.errorhandling.RocketClientException;
import sun.security.validator.ValidatorException;

import java.util.List;
import java.util.Map;

/**
 * Created by mreinhardt on 08.09.16.
 */

public interface RocketClient {

  boolean publish(String message, List<Map<String, Object>> attachments);

  boolean publish(String message, String emoji, String avatar, List<Map<String, Object>> attachments);

  void validate() throws ValidatorException, RocketClientException;
}
