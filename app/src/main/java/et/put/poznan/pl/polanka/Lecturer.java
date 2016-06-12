package et.put.poznan.pl.polanka;


public class Lecturer {
    private String name, surname, email, roomNumber, chair, floor;

    public Lecturer(String name, String surname, String email, String roomNumber, String chair, String floor) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.roomNumber = roomNumber;
        this.chair = chair;
        this.floor = floor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getChair() {
        return chair;
    }

    public void setChair(String chair) {
        this.chair = chair;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }
}
