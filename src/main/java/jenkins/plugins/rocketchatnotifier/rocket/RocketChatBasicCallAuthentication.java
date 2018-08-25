package jenkins.plugins.rocketchatnotifier.rocket;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Authentication using username/ password
 */
public class RocketChatBasicCallAuthentication implements RocketChatCallAuthentication {
  private final String serverUrl;
  private final String user;
  private final String password;
  private String authToken = "";
  private String userId = "";

  public RocketChatBasicCallAuthentication(String serverUrl, String user, String password) {
    super();
    if (!serverUrl.startsWith("http")) {
      serverUrl = "https://" + serverUrl;
    }
    if (!serverUrl.endsWith("api/")) {
      this.serverUrl = serverUrl + (serverUrl.endsWith("/") ? "" : "/") + "api/";
    }
    else {
      this.serverUrl = serverUrl;
    }
    this.user = user;
    this.password = password;
  }

  @Override
  public boolean isAuthenticated() {
    return !authToken.isEmpty() && !userId.isEmpty();
  }

  @Override
  public void doAuthentication() throws IOException {
    HttpResponse<JsonNode> loginResult;
    String apiURL = serverUrl + "v1/login";

    try {
      loginResult = Unirest.post(apiURL).field("user", user).field("password", password).asJson();
    }
    catch (UnirestException e) {
      throw new IOException("Please check if the server API " + apiURL + " is correct: " + e.getMessage(), e);
    }

    if (loginResult.getStatus() == 401) {
      throw new IOException("The username and password provided are incorrect.");
    }

    if (loginResult.getStatus() != 200) {
      throw new IOException("The login failed with a result of: " + loginResult.getStatus());
    }

    JSONObject data = loginResult.getBody().getObject().getJSONObject("data");
    this.authToken = data.getString("authToken");
    this.userId = data.getString("userId");
  }

  @Override
  public String getUrlForRequest(RocketChatRestApiV1 call) {
    return serverUrl + call.getMethodName();
  }

  @Override
  public void addAuthenticationDataToRequest(HttpRequest request) {
    request.header("X-Auth-Token", authToken);
    request.header("X-User-Id", userId);
  }
}
