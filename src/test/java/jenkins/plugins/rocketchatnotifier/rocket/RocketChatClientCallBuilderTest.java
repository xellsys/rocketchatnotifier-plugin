package jenkins.plugins.rocketchatnotifier.rocket;

import org.junit.Test;

import java.io.IOException;

public class RocketChatClientCallBuilderTest {

  @Test(expected = IOException.class)
  public void shouldEscapeSpecialCharacters() throws Exception {
    // given
    System.setProperty("http.nonProxyHost", "*.rocket.chat\\|localhost");
    RocketChatClientCallBuilder rocketCallBuilder = new RocketChatClientCallBuilder("https://open.rocket.chat", false, "]\",", "]\",");
    // when
    rocketCallBuilder.buildCall(RocketChatRestApiV1.ChannelsList);
    // then error
  }

}
