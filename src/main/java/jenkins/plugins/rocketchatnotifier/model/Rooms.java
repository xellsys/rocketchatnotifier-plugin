package jenkins.plugins.rocketchatnotifier.model;

public class Rooms {

  private Room[] rooms;

  public Room[] getRooms() {
    return rooms.clone();
  }

  public void setRooms(Room[] rooms) {
    this.rooms = rooms.clone();
  }
}
