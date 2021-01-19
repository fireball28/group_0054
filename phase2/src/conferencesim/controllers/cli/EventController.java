package conferencesim.controllers.cli;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import conferencesim.usecases.EventManager;
import conferencesim.usecases.EventStats;
import conferencesim.usecases.RoomManager;
import conferencesim.usecases.UserManager;

public class EventController extends CommandController {

	public EventController(UserManager um, EventManager em, RoomManager rm, EventStats es) {
		this.um_inst = um;
		this.em_inst = em;
		this.rm_inst = rm;
		this.es_inst = es;
	}
	
	/**
	 * Adds an event
	 * @param eventID ID of event to add
	 * @param location name of room to host event in
	 * @param startTime start time of the event
	 * @param endTime end time of the event
	 * @param speakerIDs list of speakers presenting at the event
	 */
	public void addEvent(String eventID, String location, LocalDateTime startTime, LocalDateTime endTime, List<String> speakerIDs) {
		if (speakerIDs.size() == 1) {
    		em_inst.addEvent(eventID, location, um_inst.getCurrUserID(), startTime, endTime, Optional.of(speakerIDs.get(0)), Optional.empty());
    	} else {
    		em_inst.addEvent(eventID, location, um_inst.getCurrUserID(), startTime, endTime, Optional.of(speakerIDs.get(0)), Optional.of(speakerIDs.subList(1, speakerIDs.size())));
    	}
    }
    
	/**
	 * Removes an event
	 * @param eventID ID of event to add
	 */
    public void removeEvent(String eventID) {
		if(em_inst.getEventsByOrganizer(um_inst.getCurrUserID()).contains(em_inst.getEventByID(eventID))) {
			em_inst.deleteEvents(em_inst.getEventByID(eventID));
		}
    	em_inst.deleteEvents(em_inst.getEventByID(eventID));
    }
    
    /**
     * Signs up current user for event
     * @param eventID ID of event to attend
     * @param room name of room
     */
    public void attendEvent(String eventID, String room) {
    	um_inst.userEventSignUp(um_inst.getCurrUserID(), em_inst.getEventByID(eventID), rm_inst.getRoomByName(room));
    }
    
    /**
     * Leave event
     * @param eventID ID of event to remove from user's list of attended events
     * @param room name of event
     */
    public void cancelAttend(String eventID, String room) {
    	um_inst.userEventCancel(um_inst.getCurrUserID(), em_inst.getEventByID(eventID), rm_inst.getRoomByName(room));
    }

    /**
     * Show most attended events
     */
    public void mostAttended() {
		es_inst.mostAttended();
	}

//    /**
//     * Show longest event
//     */
//	public void longestEvent() { es_inst.longestEvent(); }
//
//	/**
//	 * Show earliest event on given date
//	 * @param day date on which event is presented
//	 */
//	public void earliestEvent(LocalDateTime day) {
//		es_inst.earliestEvent(day);
//	}

	/**
	 * Show event with most speakers
	 */
	public void eventWithMostSpeakers(){
		es_inst.eventWithMostSpeakers();
	}

	/**
	 * Show top five most attended events
	 */
	public void topFiveAttended(){ es_inst.topFiveAttended(); }
    
	/**
	 * View events given by current user, if current user is a Speaker
	 */
    public void viewSpeakerEvents() {
    	em_inst.viewSpeakerEvents(um_inst.getCurrUserID());
    }
    
    /**
     * View all events registered
     */
    public void viewEvents() {
    	em_inst.viewEvents();
    }
    
    /**
     * View all events registered in a given room
     * @param location room name of events to display
     */
    public void viewEventsByLocation(String location) {
    	em_inst.viewEventsByLocation(location);
    }
    
    /**
     * View all events by time given
     * @param time time of events to display
     */
    public void viewEventsByTime(LocalDateTime time) {
    	em_inst.viewEventsByTime(time);
    }
    
    /**
     * Display all registered attendees
     */
    public void getFellowAttendees() {
    	em_inst.viewFellowAttendees(um_inst.getCurrUserID());
    }
	
	@Override
	public Method getApplicableMethod(String command, int argLen) throws NoSuchElementException {
		Method[] m_arr = this.getClass().getMethods();
		Optional<Method> om = Arrays.asList(m_arr).stream().filter(m -> m.getName().equalsIgnoreCase(command) && m.getParameterCount() == argLen).findFirst();
		return om.orElse(null);
	}

	@Override
	public void execute(String command, List<Object> callArgs) {
		try {
			getApplicableMethod(command, callArgs.size()).invoke(this, callArgs.toArray());
		} catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException exc) {
			exc.printStackTrace();
		}
	}
}
