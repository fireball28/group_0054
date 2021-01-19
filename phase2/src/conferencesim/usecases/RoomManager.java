package conferencesim.usecases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import conferencesim.entities.eventpack.Room;
import conferencesim.entities.eventpack.Court;
import conferencesim.entities.eventpack.Auditorium;
import conferencesim.entities.eventpack.Classroom;
import conferencesim.entities.eventpack.roomBuilder;
import conferencesim.gateways.EventGateway;

public class RoomManager extends Usecase implements Serializable {

    private List<Room> roomList;
    private RoomUpdater ru_obj = new EventGateway().loadFile();

    /**
     * Constructs an empty RoomManager and instantiates the roomList
     */
    public RoomManager() { this.roomList = new ArrayList<>(); }

    public void setRoomUpdater(EventManager em) {
        this.ru_obj = em;
    }

    /**
     * @return the entire rooms list
     */
    public List<Room> getAllRooms() {
        return this.roomList;
    }

    /**
     * @param roomName The name of the room
     * @return the room object (if one exists with that name) or else, null
     */
    public Room getRoomByName(String roomName) {
        for (Room r: this.roomList){
            if (r.getRoomName().equals(roomName)){
                return r; }
        }
        return null;
    }

    /**
     * @param roomName The name of the room
     * @param capacity How many people can be in the room at once
     * @param kind The type of room to create
     * Ensures no rooms of the same name are created
     * @return true if the room is created successfully, false otherwise
     */

    public boolean addRoom(String roomName, int capacity, String kind){

        for (Room r: this.roomList){
            if(r.getRoomName().equals(roomName)){
                eView.callView().roomAlreadyAdded();
                return false;
            }
        }

        if(!(kind.equalsIgnoreCase(("Auditorium")) || kind.equalsIgnoreCase(("Classroom"))|| kind.equalsIgnoreCase(("Court")))){
            view.print("The room kind you entered is invalid");
            return false;
        }

        Room toAdd = new roomBuilder().setCapacity(capacity).setRoomName(roomName).setRoomKind(kind).buildRoom();
        this.roomList.add(toAdd);
        ru_obj.updateRoom(toAdd);

        view.callView().print("Successful!");
        return true;
    }

    /**
     * @param roomName The name of the room to remove
     * @return true if the room is removed successfully, false otherwise
     */

    public void removeRoom(String roomName){
    	int sizeBefore = roomList.size();
        for (Iterator<Room> it = roomList.iterator(); it.hasNext(); ){
            Room r = it.next();
        	if (r.getRoomName().equals(roomName)){
        		ru_obj.removeRoomObs(r);
                it.remove();
            }
        }
        if (roomList.size() == sizeBefore) {
        	eView.callView().roomDoesNotExist();
        }
    }

    public void viewAllRooms() {

        List<String> roomList = new ArrayList<>();

        for(Room r: getAllRooms()){
            roomList.add("Room name: " + r.getRoomName() + " Capacity: " + String.valueOf(r.getCapacity()) + "\n");
        }
        view.printList(roomList);
    }

    public void viewRoomTechnologies(String roomName) {

        for(Room r: getAllRooms()){
            if(r.getRoomName().equals(roomName)){

                if (r instanceof Court){ view.printList(((Court)r).getTechnologies()); }

                if (r instanceof Auditorium){ view.printList(((Auditorium)r).getTechnologies()); }

                if (r instanceof Classroom){ view.printList(((Classroom)r).getTechnologies()); }
            }
        }
    }
}