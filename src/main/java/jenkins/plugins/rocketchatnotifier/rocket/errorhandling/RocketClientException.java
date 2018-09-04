package jenkins.plugins.rocketchatnotifier.rocket.errorhandling;

import com.mashape.unirest.http.exceptions.UnirestException;

public class RocketClientException extends Exception {

  public RocketClientException(Exception e) {
    super(e);
  }

  public RocketClientException(String s) {
    super(s);
  }

  public RocketClientException(String s, UnirestException e) {
    super(s, e);
  }
}
