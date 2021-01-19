package conferencesim.entities.eventpack;

import java.io.Serializable;

public class Room implements Serializable {

    private final String roomName;
    private final int capacity;

    public Room(String roomName, int capacity){

        this.roomName = roomName;
        this.capacity = capacity;
    }

    public String getRoomName(){
        return this.roomName;
    }

    public int getCapacity(){ return this.capacity; }

}
