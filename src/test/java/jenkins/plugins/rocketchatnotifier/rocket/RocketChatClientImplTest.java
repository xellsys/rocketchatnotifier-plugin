package jenkins.plugins.rocketchatnotifier.rocket;

import jenkins.plugins.rocketchatnotifier.model.Info;
import jenkins.plugins.rocketchatnotifier.model.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.Map;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(PowerMockRunner.class)
@PrepareForTest({RocketChatClientCallBuilder.class, RocketChatClientImpl.class})
public class RocketChatClientImplTest {

  private RocketChatClientImpl rocketChatClient;

  @Mock
  private RocketChatClientCallBuilder callBuilder;

  @Before
  public void setup() throws Exception {
    callBuilder = mock(RocketChatClientCallBuilder.class);
    PowerMockito.whenNew(RocketChatClientCallBuilder.class).withArguments(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()).thenReturn(callBuilder);
    rocketChatClient = new RocketChatClientImpl("", "", "");
  }

  @Test(expected = IOException.class)
  public void shouldFailOnEmptyResponseOnVersionInfo() throws Exception {
    // given
    final Response errorResponse = new Response();
    errorResponse.setSuccess(false);
    when(callBuilder.buildCall(RocketChatRestApiV1.Info)).thenReturn(errorResponse);
    // when
    rocketChatClient.getInfo();
    // then error
  }

  @Test(expected = IOException.class)
  public void shouldFailOnEmptyResponseOnSendMessage() throws Exception {
    // givenv
    final Response infoResponse = new Response();
    infoResponse.setSuccess(true);
    final Info info = new Info();
    info.setVersion("0.52.1");
    infoResponse.setInfo(info);
    final Response errorResponse = new Response();
    errorResponse.setSuccess(false);
    when(callBuilder.buildCall(RocketChatRestApiV1.Info)).thenReturn(infoResponse);
    when(callBuilder.buildCall(any(RocketChatRestApiV1.class), any(RocketChatQueryParams.class), any(Map.class))).thenReturn(errorResponse);
    // when
    rocketChatClient.send("room", "message", null, null, null);
    // then error
  }

}
