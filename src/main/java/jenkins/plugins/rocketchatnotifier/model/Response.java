package jenkins.plugins.rocketchatnotifier.model;


public class Response {
  private boolean success;
  private Message[] messages;
  private Message message;
  private User[] users;
  private User user;
  private Room[] channels;
  private Room channel;

  public void setSuccess(boolean result) {
    this.success = result;
  }

  public boolean isSuccessful() {
    return this.success;
  }

  public void setMessages(final Message[] messages) {
    this.messages = messages.clone();
  }

  public Message[] getMessages() {
    return this.messages.clone();
  }

  public boolean isMessages() {
    return this.messages != null;
  }

  public void setMessage(final Message message) {
    this.message = message;
  }

  public Message getMessage() {
    return this.message;
  }

  public boolean isMessage() {
    return this.message != null;
  }

  public void setUsers(final User[] users) {
    this.users = users.clone();
  }

  public User[] getUsers() {
    return this.users.clone();
  }

  public Room[] getChannels() {
    return this.channels.clone();
  }

  public boolean isUsers() {
    return this.users != null;
  }

  public void setUser(final User user) {
    this.user = user;
  }

  public User getUser() {
    return this.user;
  }

  public boolean isUser() {
    return this.user != null;
  }
}
