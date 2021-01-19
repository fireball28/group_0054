package conferencesim.usecases;

import conferencesim.entities.Event;
import conferencesim.entities.User;

import java.util.List;

/**
 * A class that represents an attendance manager.
 */

public class AttendenceManager {

    public Event event;
    public User user;

    /**
     * Creates an attendance manager that keeps track of users in an event.
     * @param event the event that is part of the conference.
     * @param user user that attends the event
     */
    public AttendenceManager(Event event, User user){
        this.event = event;
        this.user = user;
    }

    public List<String> getAttendees(){
        return event.getAttendees();
    }

    public boolean addUser(){
        return event.addAttendee(user.getUserID());
    }

    public boolean removeUser(){
        return event.removeAttendee(user.getUserID());
    }


}
