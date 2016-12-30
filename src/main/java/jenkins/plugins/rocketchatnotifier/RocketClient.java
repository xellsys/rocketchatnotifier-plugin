package jenkins.plugins.rocketchatnotifier;


import sun.security.validator.ValidatorException;

import java.io.IOException;

/**
 * Created by mreinhardt on 08.09.16.
 */
public interface RocketClient {

  boolean publish(String message);

  void validate() throws ValidatorException, IOException;
}
