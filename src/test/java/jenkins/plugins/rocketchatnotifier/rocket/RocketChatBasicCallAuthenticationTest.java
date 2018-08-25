package jenkins.plugins.rocketchatnotifier.rocket;

import org.junit.Test;

import java.io.IOException;

public class RocketChatBasicCallAuthenticationTest {

  @Test(expected = IOException.class)
  public void shouldAutoPrefixWithHttpsIfNotGiven() throws Exception {
    new RocketChatBasicCallAuthentication("example.com", "a", "b").doAuthentication();
  }
}
