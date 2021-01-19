package conferencesim.entities.eventpack;

public class roomBuilder {

    private String roomName;
    private String roomKind;
    private int capacity;

    public roomBuilder setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public roomBuilder setRoomName(String roomName) {
        this.roomName = roomName;
        return this;
    }

    public roomBuilder setRoomKind(String roomKind) {
        this.roomKind = roomKind;
        return this;
    }

    public Room buildRoom(){

        if(roomKind.equalsIgnoreCase("Auditorium")){
            return new Auditorium(roomName,capacity);
        }

        if(roomKind.equalsIgnoreCase("Classroom")){
            return new Classroom(roomName,capacity);
        }

        if(roomKind.equalsIgnoreCase("Court")){
            return new Court(roomName,capacity);
        }

        return null;
    }
}
