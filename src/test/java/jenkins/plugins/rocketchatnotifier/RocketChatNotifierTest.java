package jenkins.plugins.rocketchatnotifier;

import jenkins.model.Jenkins;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Jenkins.class, RocketChatNotifier.class})
public class RocketChatNotifierTest {

  @Mock
  private Jenkins jenkins;

  RocketChatNotifier notifier;

  @Before
  public void setup() throws Exception {
    MockitoAnnotations.initMocks(this);
    PowerMockito.mockStatic(Jenkins.class);
    PowerMockito.when(Jenkins.getInstance()).thenReturn(jenkins);
    File rootPath = new File(System.getProperty("java.io.tmpdir"));
    when(jenkins.getRootDir()).thenReturn(rootPath);
    notifier = new RocketChatNotifier(
      "rocket.example.com", false,
      "user", "password",
      "jenkins", "rocket.example.com",
      false,
      false, false, false, false, false, false, false, false, null, false, null, null, null);
  }

  @Test
  public void shouldFallbackToJenkinsUrlIfBuildServerUrlIsNotProvived() throws Exception {
    // given
    notifier.setBuildServerUrl(null);
    // when
    notifier.getBuildServerUrl();
    // then
  }

  @Test
  public void shouldProvideBuildServerUrl() throws Exception {
    // given
    // when
    String serverUrl = notifier.getBuildServerUrl();
    // then
    assertThat(serverUrl, is(not(nullValue())));
  }
}
