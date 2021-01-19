package conferencesim.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * An instance of this class represents an Event at the conference
 */

public class Event implements Serializable {

    private LocalDateTime time;
    private String location;
    private String organizerID;
    private String eventID;
    private int capacity = 2;
    // make this a List of speakers as it can be more than one
    private String speakerID;
    private List<String> attendees = new ArrayList<>();

    /**
     * We require the time, location, and eventID in order to create an instance of Event
     * @param time of the event.
     * @param location of the event.
     * @param eventID of the event.
     */

    public Event(LocalDateTime time, String location, String eventID){
        this.time = time;
        this.location = location;
        this.eventID = eventID;
        this.organizerID = "Default Organizer";
        this.speakerID = "TBA";
    }

    public void setTime(LocalDateTime time){
        this.time = time;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int x) {
        if (x > 0) {
            this.capacity = x;
        }
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void setOrganizer(String organizerID){
        this.organizerID = organizerID;
    }

    public void setSpeakerID(String speakerID) {
        this.speakerID = speakerID;
    }

    public LocalDateTime getTime(){
        return time;
    }

    public LocalDateTime getLocalDateTime() {
        return time;
    }

    public String getLocation(){
        return location;
    }

    public String getEventID() { return eventID;}

    public String getOrganizer(){
        return organizerID;
    }

    public List<java.lang.String> getAttendees() {
        return attendees;
    }

    public String getSpeakerID() {
        return speakerID;
    }

    /**
     * Add attendee to the Event if the attendees list contains userID. Return true if the user id that is being added
     * does not already exist in attendees and that the size of the attendees does not exceed the maximum capacity.
     * @param userID user id that is being added to attendees.
     * @return true if the user id that is being added does not already exist in attendees and that the size of the
     * attendees does not exceed the maximum capacity.
     */
    public boolean addAttendee(String userID){
        if(attendees.contains(userID) || attendees.size() >= capacity){
            return false;
        }
        attendees.add(userID);
        return true;
    }

    /**
     * Remove attendees to the Event if the attendees list contains userID. Return true if attendees contains the user
     * id that is removed.
     * @param userID that is being removed from attendees.
     * @return true if attendees contains the user id that is removed.
     */
    public boolean removeAttendee(String userID) {
        if(attendees.contains(userID)) {
            this.attendees.remove(userID);
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return "" + this.getEventID() + ", " + this.getLocation() + ", " + this.getTime();
    }

}
