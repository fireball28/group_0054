package conferencesim.entities.eventpack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.Duration;

public class Event implements Serializable {
    private String eventID;
    private String location;
    private String organizerID;
    private List<String> attendees = new ArrayList<>();
    private LocalDateTime startTime, endTime;
    private int eventOccupancy;

    public Event(String eventID, String roomID, String organizerID, LocalDateTime startTime, LocalDateTime endTime) {
        this.eventID = eventID;
        this.location = roomID;
        this.organizerID = organizerID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventOccupancy = 0;
    }

    public String getEventID(){
        return this.eventID;
    }

    public String getSpeakerID() {
        return "";
    }

    public List<String> getSpeakers() {
        return new ArrayList<>();
    }

    public void setEventID(String newEventID) {
        this.eventID = newEventID;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String newLocation) { //check for roomname in EventManager
        this.location = newLocation;
    }

    public String getOrganizerID() {
        return this.organizerID;
    }

    public void setOrganizerID(String newOrganizerID) {
        this.organizerID = newOrganizerID;
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Duration getDuration() {
        return Duration.between(startTime, endTime);
    }

    public List<String> getAttendees() {
        return attendees;
    }

    public int getEventOccupancy() { return eventOccupancy; }

    /**
     * Add attendee to the Event if the attendees list contains userID. Return true if the user id that is being added
     * does not already exist in attendees and that the size of the attendees does not exceed the maximum capacity.
     * @param userID user id that is being added to attendees.
     */
    public void addAttendee(String userID) {

        this.attendees.add(userID);
        this.eventOccupancy ++;
    }

    /**
     * Remove attendees to the Event if the attendees list contains userID. Return true if attendees contains the user
     * id that is removed.
     * @param userID that is being removed from attendees.
     */
    public void removeAttendee(String userID) {

        this.attendees.remove(userID);
        this.eventOccupancy--;
    }

    public boolean equals(Event e) {
        return this.getEventID().equals(e.getEventID());
    }
    
    @Override
    public String toString() {
    	return this.eventID + " is taking place in " + this.getLocation() + " at " + this.getStartTime();
    }
}
