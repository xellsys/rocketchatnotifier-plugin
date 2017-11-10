package jenkins.plugins.rocketchatnotifier.model;

public class Rooms {

  private Room[] rooms = new Room[0];

  public Room[] getRooms() {
    return rooms.clone();
  }

  public void setRooms(Room[] rooms) {
    this.rooms = rooms.clone();
  }
}
