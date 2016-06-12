package et.put.poznan.pl.polanka;

public class Room {
    private String roomNumber;
    private int floor;
    private float x, y;

    public Room(String roomNumber, int floor, float x, float y) {
        this.roomNumber = roomNumber;
        this.floor = floor;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
