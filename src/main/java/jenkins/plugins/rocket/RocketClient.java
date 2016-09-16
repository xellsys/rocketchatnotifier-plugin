package jenkins.plugins.rocket;


/**
 * Created by mreinhardt on 08.09.16.
 */
public interface RocketClient {

  boolean publish(String message);
}
