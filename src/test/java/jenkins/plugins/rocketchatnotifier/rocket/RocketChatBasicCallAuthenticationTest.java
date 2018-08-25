package jenkins.plugins.rocketchatnotifier.rocket;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RocketChatBasicCallAuthenticationTest {

  @Test
  public void shouldNotAppendSlashToRootUrlIfAlreadyGiven() throws Exception {
    RocketChatBasicCallAuthentication chatBasicCallAuthentication = new RocketChatBasicCallAuthentication("example.com/", "a", "b");
    String sampleCall = chatBasicCallAuthentication.getUrlForRequest(RocketChatRestApiV1.Info);
    assertThat(sampleCall, is(equalTo("https://example.com/api/v1/info")));
  }

  @Test
  public void shouldAppendApiPathIfNotGiven() throws Exception {
    RocketChatBasicCallAuthentication chatBasicCallAuthentication = new RocketChatBasicCallAuthentication("example.com", "a", "b");
    String sampleCall = chatBasicCallAuthentication.getUrlForRequest(RocketChatRestApiV1.Info);
    assertThat(sampleCall, is(equalTo("https://example.com/api/v1/info")));
  }

  @Test
  public void shouldNotAppendApiPathIfiven() throws Exception {
    RocketChatBasicCallAuthentication chatBasicCallAuthentication = new RocketChatBasicCallAuthentication("example.com/api", "a", "b");
    String sampleCall = chatBasicCallAuthentication.getUrlForRequest(RocketChatRestApiV1.Info);
    assertThat(sampleCall, is(equalTo("https://example.com/api/v1/info")));
  }

  @Test(expected = IOException.class)
  public void shouldAutoPrefixWithHttpsIfNotGiven() throws Exception {
    new RocketChatBasicCallAuthentication("example.com", "a", "b").doAuthentication();
  }
}
