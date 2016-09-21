package jenkins.plugins.rocketchatnotifier;


/**
 * Created by mreinhardt on 08.09.16.
 */
public interface RocketClient {

  boolean publish(String message);
}
