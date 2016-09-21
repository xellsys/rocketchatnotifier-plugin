package jenkins.plugins.rocketchatnotifier;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import jenkins.plugins.rocketchatnotifier.model.Message;
import jenkins.plugins.rocketchatnotifier.model.Room;
import jenkins.plugins.rocketchatnotifier.model.Rooms;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by mreinhardt on 08.09.16.
 */
public class RocketClientImpl implements RocketClient {

  public static final String API_PATH = "/api/";

  private RocketChatClient client;

  private String channel;


  public RocketClientImpl(String serverUrl, String user, String password, String channel) {
    this.client = new RocketChatClient(serverUrl + API_PATH, user, password);
    this.channel = channel;
  }

  public boolean publish(String message) {
    try {
      this.client.send(this.channel, message);
      return true;
    } catch (IOException e) {
      return false;
    }
  }
}


class RocketChatClient {
  private final String serverUrl;
  private final String user;
  private final String password;
  private String xAuthToken;
  private String xUserId;
  private ObjectMapper jacksonObjectMapper;
  Map<String, Room> roomCache = new HashMap();
  JSONObject lazyVersions;

  public RocketChatClient(String serverUrl, String user, String password) {
    this.serverUrl = serverUrl;
    this.user = user;
    this.password = password;
    this.jacksonObjectMapper = new ObjectMapper();
    this.jacksonObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  public Set<Room> getPublicRooms() throws IOException {
    Rooms rooms = (Rooms) this.authenticatedGet("publicRooms", Rooms.class);
    HashSet ret = new HashSet();
    this.roomCache.clear();
    Room[] roomsArray = rooms.getRooms();
    int numberOfRooms = roomsArray.length;

    for (int i = 0; i < numberOfRooms; ++i) {
      Room room = roomsArray[i];
      if (room.getId() == null) {
        room.setId(room.getName());
      }
      ret.add(room);
      this.roomCache.put(room.getName(), room);
    }

    return ret;
  }

  private <T> T authenticatedGet(String method, Class<T> reponseClass) throws IOException {
    try {
      HttpResponse e = Unirest.get(this.serverUrl + method).header("X-Auth-Token", this.xAuthToken).header("X-User-Id", this.xUserId).asString();
      if (e.getStatus() == 401) {
        this.login();
        return this.authenticatedGet(method, reponseClass);
      } else {
        return this.jacksonObjectMapper.readValue((String) e.getBody(), reponseClass);
      }
    } catch (UnirestException var4) {
      throw new IOException(var4);
    }
  }

  private void authenticatedPost(String method, Object request) throws IOException {
    this.authenticatedPost(method, request, (Class) null);
  }

  private <T> T authenticatedPost(String method, Object request, Class<T> reponseClass) throws IOException {
    try {
      HttpResponse e = Unirest.post(this.serverUrl + method).header("X-Auth-Token", this.xAuthToken).header("X-User-Id", this.xUserId).header("Content-Type", "application/json").body(this.jacksonObjectMapper.writeValueAsString(request)).asString();
      if (e.getStatus() == 401) {
        this.login();
        return this.authenticatedPost(method, request, reponseClass);
      } else {
        return reponseClass == null ? null : this.jacksonObjectMapper.readValue((String) e.getBody(), reponseClass);
      }
    } catch (UnirestException var5) {
      throw new IOException(var5);
    }
  }

  void login() throws UnirestException {
    HttpResponse asJson = Unirest.post(this.serverUrl + "login").field("user", this.user).field("password", this.password).asJson();
    if (asJson.getStatus() == 401) {
      throw new UnirestException("401 - Unauthorized");
    } else {
      JSONObject data = ((JsonNode) asJson.getBody()).getObject().getJSONObject("data");
      this.xAuthToken = data.getString("authToken");
      this.xUserId = data.getString("userId");
    }
  }

  public void logout() throws IOException {
    try {
      Unirest.post(this.serverUrl + "logout").header("X-Auth-Token", this.xAuthToken).header("X-User-Id", this.xUserId).asJson();
    } catch (UnirestException var2) {
      throw new IOException(var2);
    }
  }

  public String getApiVersion() throws IOException {
    return this.getVersions().getString("api");
  }

  public String getRocketChatVersion() throws IOException {
    return this.getVersions().getString("rocketchat");
  }

  private JSONObject getVersions() throws IOException {
    if (this.lazyVersions == null) {
      try {
        this.lazyVersions = ((JsonNode) Unirest.get(this.serverUrl + "version").asJson().getBody()).getObject().getJSONObject("versions");
      } catch (UnirestException var2) {
        throw new IOException(var2);
      }
    }

    return this.lazyVersions;
  }

  public void send(String roomName, String message) throws IOException {
    Room room = this.getRoom(roomName);
    if (room == null) {
      throw new IOException(String.format("unknown room : %s", new Object[]{roomName}));
    } else {
      this.send(room, message);
    }
  }

  public void send(Room room, String message) throws IOException {
    this.authenticatedPost("rooms/" + room.getId() + "/send", new Message(message));
  }

  public Room getRoom(String room) throws IOException {
    Room ret = (Room) this.roomCache.get(room);
    if (ret == null) {
      this.getPublicRooms();
      ret = (Room) this.roomCache.get(room);
    }

    return ret;
  }
}

