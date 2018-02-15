package jenkins.plugins.rocketchatnotifier.utils;

import hudson.ProxyConfiguration;
import hudson.util.Secret;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Secret.class, NetworkUtils.class})
public class NetworkUtilsTest {


  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    PowerMockito.mockStatic(Secret.class);
  }

  @Test
  public void shouldNotUseProxyIfHostIsOnNonProxyList() throws Exception {
    // given
    ProxyConfiguration proxyConfiguration = new ProxyConfiguration("sample1", 1234, null, null, "*.test.com|localhost");
    // when
    boolean needsProxy = NetworkUtils.isHostOnNoProxyList("http://rocket.test.com", proxyConfiguration);
    // then
    assertThat(needsProxy, is(true));
  }

  @Test
  public void shouldUseProxyIfHostIsNotOnNonProxyList() throws Exception {
    // given
    ProxyConfiguration proxyConfiguration = new ProxyConfiguration("sample1", 1234, null, null, "*.test.com|localhost");
    // when
    boolean needsProxy = NetworkUtils.isHostOnNoProxyList("http://rocket.test2.com", proxyConfiguration);
    // then
    assertThat(needsProxy, is(false));
  }
}
