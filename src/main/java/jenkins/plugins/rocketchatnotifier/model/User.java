package jenkins.plugins.rocketchatnotifier.model;

import java.util.Date;

/**
 * Represents a Rocket.Chat user.
 *
 * @author Bradley Hilton (graywolf336)
 * @version 0.0.1
 * @since 0.1.0
 */
public class User extends Identified {
  private String username, name;
  private Date createdAt = new Date();
  private Date lastLogin = new Date();
  private boolean active;
  private int utcOffset;

  /**
   * Sets the username of this user.
   *
   * @param username of this user
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Gets the username of this user.
   *
   * @return user of this user
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * Sets the display name of this user.
   *
   * @param name of this user
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Gets the display name of this user.
   *
   * @return the name of this user
   */
  public String getName() {
    return this.name;
  }

  /**
   * Sets the created date of this user.
   *
   * @param date this user was created
   */
  public void setCreatedAt(Date date) {
    this.createdAt = new Date(date.getTime());
  }

  /**
   * Gets the date this user was created
   *
   * @return creation date of this user
   */
  public Date getCreatedAt() {
    return new Date(this.createdAt.getTime());
  }

  /**
   * Sets the last login date of this user.
   *
   * @param date this user last logged in
   */
  public void setLastLogin(Date date) {
    this.lastLogin = new Date(date.getTime());
  }

  /**
   * Gets the date this user last logged in
   *
   * @return last logged in date
   */
  public Date getLastLogin() {
    return new Date(this.lastLogin.getTime());
  }

  /**
   * Sets whether this user is active or not
   *
   * @param active whether this user is active or not
   */
  public void setActive(boolean active) {
    this.active = active;
  }

  /**
   * Gets whether this user is active or not, non-active users can not log in.
   *
   * @return whether this user is logged in or not
   */
  public boolean getActive() {
    return this.active;
  }

  /**
   * Checks whether this user is active or not, non-active users can not log in.
   *
   * @return whether this user is logged in or not
   */
  public boolean isActive() {
    return this.active;
  }

  /**
   * Sets the UTC Offset of this user.
   *
   * @param offset of this user
   */
  public void setUtcOffset(int offset) {
    this.utcOffset = offset;
  }

  /**
   * Gets the UTC Offset of this user
   *
   * @return utc offset for this user
   */
  public int getUtcOffset() {
    return this.utcOffset;
  }
}

/*
 * "type": "user",//TODO: Convert to ENUM "status": "offline",//TODO: Convert to ENUM "roles": [
 * "user" ], "statusConnection": "offline",//TODO: Convert to ENUM
 */
