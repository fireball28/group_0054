package conferencesim.usecases;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import conferencesim.entities.eventpack.Event;
import conferencesim.entities.eventpack.EventBuilder;
import conferencesim.entities.eventpack.Room;


public class EventManager extends Usecase implements Serializable, RoomUpdater {

    private List<Event> eventList;
    private List<Room> roomList;

    /**
     * Creates a blank event manager with empty eventList and rooms
     */
    public EventManager() {
        this.eventList = new ArrayList<>();
        this.roomList = new ArrayList<>();
    }

    /**
     * Creates an event manager with a list of events and rooms.
     *
     * @param eventList list of events.
     */
    public EventManager(List<Event> eventList) {
        this.eventList = eventList;
    }


    public List<Event> getEventList() {
        return this.eventList;
    }

    /**
     * Returns list of AttendeeIDs that are attending the event of interest. returns null and calls presenter if no
     * event with param eventID can be found in eventList.
     *
     * @param eventID eventID of interest
     */

    public List<String> getEventAttendees(String eventID) {
        Optional<Event> eventOpt = this.getEventList().stream().filter(e -> e.getEventID().equals(eventID)).findFirst();
        if (eventOpt.isPresent()) {
            return eventOpt.get().getAttendees();
        } else {
            eView.callView().eventNotFound();
            return null;
        }
    }

    /**
     * Checks if a time conflicts with currently scheduled events.
     * Each event has a duration which is compared to
     *
     * @param startTime LocalDateTime
     * @return boolean indicating a time conflict
     */
    public boolean eventTimeCoincides(LocalDateTime startTime) {
        Stream<Event> eventStream = this.getEventList().stream().filter(e -> e.getStartTime().toLocalDate()
                .isEqual(startTime.toLocalDate()));
        return eventStream.anyMatch(e -> Duration.between(e.getStartTime(), startTime).toMinutes()
                < e.getDuration().toMinutes());
    }

    public boolean eventCoincides(Event event) {
        for (Event e: this.getEventsByLocation(event.getLocation())) {
            if (Duration.between(e.getStartTime(), event.getStartTime()).toMinutes() < e.getDuration().toMinutes()) {
                eView.callView().eventCoincides();
                return true;
            }
        }

        return false;
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
    public List<Event> getEventsBySpeaker(String speakerID) {
        List<Event> eventsBySpeaker = new ArrayList<>();
        for (Event e : this.getEventList()) {
            if (e.getSpeakerID().equals(speakerID) || e.getSpeakers().contains(speakerID)) {
                eventsBySpeaker.add(e);
            }
        }
        return eventsBySpeaker;
    }

    /**
     * Return a list containing all Events organized by the same user (organizerID).
     * @param organizerID id of the organizer.
     * @return the list containing all Events with the same organizer.
     */
    public List<Event> getEventsByOrganizer(String organizerID) {
        return this.getEventList().stream().filter(e -> e.getOrganizerID().equals(organizerID))
                .collect(Collectors.toList());
    }

    /**
     * Return a list containing all Events that take place at the same location (roomName).
     * @param roomName name of room or location.
     * @return the list containing all Events that take place at the same location.
     */

    public List<Event> getEventsByLocation(String roomName) {
        return this.getEventList().stream().filter(e -> e.getLocation().equals(roomName)).collect(Collectors.toList());
    }

    /**
     * Return a list containing all Events that take place at @param time
     * @param time LocalDateTime value
     * @return the list containing all Events that take place at @param time.
     */
    public List<Event> getEventsAtTime(LocalDateTime time) {
        return this.getEventList().stream().filter(e -> Duration.between(e.getStartTime(), time).toMinutes()
                < e.getDuration().toMinutes()).collect(Collectors.toList());
    }

    /**
     * Return the list containing all Events that contains the attendee with userID in its attendees list.
     * @param userID id of the user that is used to find the events.
     * @return the list containing all Events that contains the attendee with userID in its attendees list.
     */
    public List<Event> getEventsByAttendee(String userID) {
        return this.getEventList().stream().filter(e -> e.getAttendees().contains(userID)).collect(Collectors.toList());
    }

    /**
     * Retrieve an event by its event ID
     * @param eventID id of the event.
     * @return Event if event exists, null otherwise
     */
    public Event getEventByID(String eventID) {
        for (Event e: this.getEventList()) {
            if (e.getEventID().equals(eventID)) {
                return e;
            }
        }

        eView.callView().eventDoesNotExist();
        return null;
    }

    private boolean speakerAvailable(String speakerID, LocalDateTime time) {
        for (Event e1: this.getEventsBySpeaker(speakerID)) {
            for (Event e2: this.getEventsAtTime(time)) {
                if (e1.equals(e2)) {
                    eView.callView().speakerNotAvailable();
                    return false;
                }
            }
        }
        view.callView().print("Speaker is available");
        return true;
    }

    /**
     * Add an event to this event manager.
     * @param eventID of the event.
     * @param location of the event.
     * @param organizerID of the event.
     * @param startTime of the event.
     * @param endTime of the event.
     * @param speakerID of the event if it exists.
     * @param speakers of the event if they exist.
     * @return false if an existing event is already taking place at this time and venue or if the speaker is
     * unavailable for this timeslot else, return true
     */

    public boolean addEvent(String eventID, String location, String organizerID,
                            LocalDateTime startTime, LocalDateTime endTime, Optional<String> speakerID,
                            Optional<List<String>> speakers) {

        if (speakerID.isPresent() && speakers.isPresent()) {
            eView.callView().addEventsInvalidParameter();
            return false;
        }
        
        if (this.roomList.stream().noneMatch(r -> r.getRoomName().equals(location))) {
            eView.callView().roomDoesNotExist();
            return false;
        }
        
        Event event = new EventBuilder().setEventID(eventID)
                .setLocation(location)
                .setOrganizer(organizerID)
                .setTime(startTime, endTime)
                .setSpeaker(speakerID.orElse(null))
                .setSpeakers(speakers.orElse(null))
                .build();

        if (this.eventExists(eventID)) {
            eView.callView().eventExists();
            return false;
        }

        if (this.eventCoincides(event)) {
            eView.callView().eventCoincides();
            return false;
        }

        if (speakerID.isPresent() || speakers.isPresent()) {
            if (speakerID.isPresent()) {
                this.speakerAvailable(speakerID.get(), startTime);
            } else {
                for (String s : speakers.get()) {
                    if (!this.speakerAvailable(s, startTime)) {
                        eView.callView().speakerNotAvailable();;
                        return false;
                    }
                }
            }
        }

        this.eventList.add(event);
        view.callView().print("Event successfully added");
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
            view.callView().print("Event successfully deleted");
            return true;
        }
        eView.callView().eventDoesNotExist();
        return false;
    }

    public boolean viewSpeakerEvents(String userID) {
    	List<String> eventList = getEventsBySpeaker(userID).stream().map(Event::toString).collect(Collectors.toList());
    	view.printList(eventList);
    	return true;
    }
    
    public boolean viewEvents() {
    	List<String> eventList = getEventList().stream().map(Event::toString).collect(Collectors.toList());
    	view.printList(eventList);
    	return true;
    }
    
    public boolean viewEventsByLocation(String location) {
    	List<String> eventList = getEventsByLocation(location).stream().map(Event::toString).collect(Collectors.toList());
    	view.printList(eventList);
    	return true;
    }
    
    public boolean viewEventsByTime(LocalDateTime time) {
    	List<String> eventList = getEventsAtTime(time).stream().map(Event::toString).collect(Collectors.toList());
    	view.printList(eventList);
    	return true;
    }
    
    public boolean viewFellowAttendees(String userID) {
    	Stream<String> ls = getEventsByAttendee(userID).stream().map(Event::getAttendees).collect(Collectors.toList()).stream().flatMap(Collection::stream);
    	view.printList(ls.collect(Collectors.toList()));
    	return true;
    }
    
    @Override
    public void updateRoom(Room rm) {
        if (!this.roomList.contains(rm)) {
            this.roomList.add(rm);
        }
    }


    public void removeRoomObs(Room rm){
        if (this.roomList.contains(rm)) {
            this.roomList.remove(rm);
        }
    }
}