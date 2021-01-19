package conferencesim.usecases;

import conferencesim.entities.Event;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * A class that represents an event manager.
 */

public class EventManager implements Serializable {

    private List<Event> eventList;
    private List<String> rooms;

    /**
     * Creates an empty event manager.
     */
    public EventManager(){
        this.eventList = new ArrayList<>();
        this.rooms = new ArrayList<>();
    }

    /**
     * Creates an event manager with a list of events.
     * @param eventList list of events.
     */
    public EventManager(List<Event> eventList){
        this.eventList = eventList;
    }

    public List<Event> getEventList(){
        return this.eventList;
    }

    public List<String> getRooms() {
        return this.rooms;
    }

    public List<String> getEventAttendees(String eventID) {
        Optional<Event> eventOpt = eventList.stream().filter(e -> e.getEventID().equals(eventID)).findFirst();
        return eventOpt.isPresent() ? eventOpt.get().getAttendees() : null;
    }

    /**
     * Checks if a time conflicts with currently scheduled events.
     * Assumes that each event is one hour long.
     * @param time LocalDateTime
     * @return boolean indicating a time conflict
     */
    public boolean eventCoincides(LocalDateTime time) {
        Stream<Event> eventStream = eventList.stream().filter(e -> e.getLocalDateTime().toLocalDate().isEqual(time.toLocalDate()));
        return eventStream.anyMatch(e -> Duration.between(e.getLocalDateTime(), time).toMinutes() < 60);
    }

    /**
     * Returns a boolean of whether the event exists in the event list.
     * @param eventID id of the event.
     * @return a boolean of whether the event exists in the event list.
     */
    public boolean eventExists(String eventID) {
        return eventList.stream().anyMatch(e -> e.getEventID().equals(eventID));
    }

    /**
     * Return the speakerEvent list containing all Events with the same speaker (speakerID).
     * @param speakerID id of the speaker.
     * @return the speakerEvent list containing all Events with the same speaker (speakerID).
     */
    public List<Event> getEventListBySpeaker(String speakerID){
        List<Event> speakerEvent = new ArrayList<Event>();
        for(Event e: eventList){
            if(e.getSpeakerID().equals(speakerID)) {
                speakerEvent.add(e);
            }
        }
        return speakerEvent;
    }

    /**
     * Return the organizerEvent list containing all Events with the same organizer (organizerID).
     * @param organizerID id of the organizer.
     * @return the organizerEvent list containing all Events with the same organizer (organizerID).
     */
    public List<Event> getEventListByOrganizer(String organizerID){
        List<Event> organizerEvent = new ArrayList<Event>();
        for(Event e: eventList){
            if(e.getOrganizer().equals(organizerID)) {
                organizerEvent.add(e);
            }
        }
        return organizerEvent;
    }

    /**
     * Return the locationEvent list containing all Events with the same location.
     * @param location of the event.
     * @return the locationEvent list containing all Events with the same location.
     */
    public List<Event> getEventListByLocation(String location){
        List<Event> locationEvent = new ArrayList<Event>();
        for(Event e: eventList){
            if(e.getLocation().equals(location)) {
                locationEvent.add(e);
            }
        }
        return locationEvent;
    }

    /**
     * Return the eventsByTime list containing all Events with the same time.
     * @param time of the event.
     * @return the eventsByTime list containing all Events with the same time.
     */
    public List<Event> getEventListByTime(LocalDateTime time) {
        List<Event> eventsByTime = new ArrayList<Event>();
        for (Event e: eventList) {
            if (e.getTime().equals(time)) {
                eventsByTime.add(e);
            }
        }
        return eventsByTime;
    }

    /**
     * Return the attendeeEvent list containing all Events that has the attendee with userID.
     * @param userID id of the user that is used to find the events.
     * @return the attendeeEvent list containing all Events that has the attendee with userID.
     */
    public List<Event> getAttendeeEvents(String userID){
        List<Event> attendeeEvent = new ArrayList<Event>();
        for(Event e: eventList){
            if(e.getAttendees().contains(userID)) {
                attendeeEvent.add(e);
            }
        }
        return attendeeEvent;
    }

    /**
     * Retrieve an event by its event ID
     * @param eventID id of the event.
     * @return Event if event exists, null otherwise
     */
    public Event getEventByID(String eventID) {
        for (Event event : eventList) {
            if (event.getEventID().equals(eventID)) {
                return event;
            }
        }
        return null;
    }

    /**
     * Add a room to this event manager.
     * @param roomName name of the room to be added.
     * @return true if room does not already exist, false otherwise
     */
    public boolean addRoom(String roomName) {
        if (!this.getRooms().contains(roomName)) {
            this.rooms.add(roomName);
            return true;
        }
        return false;
    }

    /**
     * Remove a room from this event manager
     * @param roomName name of the room to be removed.
     * @return true if room exists, false otherwise
     */
    public boolean removeRoom(String roomName) {
        if (this.getRooms().contains(roomName)) {
            this.rooms.remove(roomName);
            return true;
        }
        return false;
    }

    /**
     * Add an event to this event manager. Return false if an existing event is already taking place at this time and
     * venue or if the speaker is unavailable for this timeslot else, return true
     * @param time of the event.
     * @param location of the event.
     * @param eventID of the event.
     * @param organizerID of the event.
     * @param speakerID of the event.
     * @return false if an existing event is already taking place at this time and venue or if the speaker is
     * unavailable for this timeslot else, return true
     */
    public boolean addEvent(LocalDateTime time, String location, String eventID, String organizerID, String speakerID){
        if (!this.getRooms().contains(location)) {
            System.out.println("[Error] location not in system, add room first");
            return false;
        }
        Event event = new Event(time, location, eventID);
        event.setOrganizer(organizerID);
        event.setSpeakerID(speakerID);

        for(Event e: eventList){
            if(e.getTime().equals(time) && e.getLocation().equals(location)){
                System.out.println("An event is already taking place at this time and venue");
                return false;
            }
        }

        List<Event> temp = this.getEventListBySpeaker(speakerID);

        for(Event e: temp){
            if(e.getTime().equals(time)){
                System.out.println("Speaker unavailable for this time slot");
                return false;
            }
        }

        this.eventList.add(event);
        return true;
    }

    /**
     * Delete an event from this event manager.
     * @param event to be removed from the event manager.
     * @return true if the event list contains the event and the event is removed from the event list else, return
     * false.
     */
    public boolean deleteEvents(Event event){
        if(this.eventList.contains(event)) {
            this.eventList.remove(event);
            return true;
        }
        return false;
    }

}