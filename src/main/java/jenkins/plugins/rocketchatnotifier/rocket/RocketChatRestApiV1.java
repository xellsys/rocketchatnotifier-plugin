package jenkins.plugins.rocketchatnotifier.rocket;

/**
 * Enumeration of the available REST API methods.
 *
 * @author Bradley Hilton (graywolf336)
 * @version 0.0.1
 * @since 0.1.0
 */
public enum RocketChatRestApiV1 {
  /**
   * Retrieves a list of all the users in the server.
   */
  UsersList("users.list", HttpMethods.GET, true), /**
   * Retrieves the user information from the server.
   */
  UsersInfo("users.info", HttpMethods.GET, true), /**
   * Retrieves a list of all the channels in the server.
   */
  ChannelsList("channels.list", HttpMethods.GET, true), /**
   * Retrieves a list of all the channels in the server.
   */
  ChannelsInfo("channels.info", HttpMethods.GET, true), /**
   * Retrieves a list of all the channels in the server.
   */
  PostMessage("chat.postMessage", HttpMethods.POST, true), /**
   * Retrieves miscellaneous information from rocket.chat instance, like version number
   */
  Info("info", HttpMethods.GET, false);

  private String methodName;
  private HttpMethods httpMethod;
  private boolean requiresAuth;

  private RocketChatRestApiV1(String methodName, HttpMethods httpMethod, boolean requiresAuth) {
    this.methodName = methodName;
    this.httpMethod = httpMethod;
    this.requiresAuth = requiresAuth;
  }

  /**
   * Gets the method name to be called to the server.
   *
   * @return the method name plus "v1/" at the start
   */
  public String getMethodName() {
    return "v1/" + this.methodName;
  }

  /**
   * Gets the {@link HttpMethods http method} which should be used.
   *
   * @return {@link HttpMethods http method} to be used
   */
  public HttpMethods getHttpMethod() {
    return this.httpMethod;
  }

  /**
   * Check whether the method requires authentication or not.
   *
   * @return whether this requires authentication or not
   */
  public boolean requiresAuth() {
    return this.requiresAuth;
  }
}
