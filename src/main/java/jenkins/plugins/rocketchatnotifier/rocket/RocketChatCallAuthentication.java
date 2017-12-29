package jenkins.plugins.rocketchatnotifier.rocket;

import java.io.IOException;

import com.mashape.unirest.request.HttpRequest;

public interface RocketChatCallAuthentication {

  boolean isAuthenticated();

  void doAuthentication() throws IOException;

  String getUrlForRequest(RocketChatRestApiV1 call);

  void addAuthenticationDataToRequest(HttpRequest request);
}
