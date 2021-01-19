package conferencesim.controllers;

import conferencesim.entities.Event;
import conferencesim.usecases.EventManager;
import conferencesim.usecases.Messenger;
import conferencesim.usecases.UserManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SpeakerController implements Commandable {

    private UserManager um_inst;
    private EventManager em_inst;

    private Messenger messenger;

    public SpeakerController(UserManager um_inst, EventManager em_inst, Messenger messenger) {
        this.um_inst = um_inst;
        this.em_inst = em_inst;

        this.messenger = messenger;
    }

    @Override
    public List<String> getCommandList() {
        return Arrays.asList("messageAllAttendeesOfEvent", "viewSpeakerEvents");
    }

    @Override
    public boolean run(Scanner sc, String command) {
        if (command.equalsIgnoreCase("messageAllAttendeesOfEvent")) {
            return messageAllAttendeesOfEvent(sc);
        } else if (command.equalsIgnoreCase("messageAllEventAttendees")) {
            return messageAllAttendeesOfSpeakerEvents(sc);
        } else if (command.equalsIgnoreCase("viewSpeakerEvents")) {
            return viewSpeakerEvents();
        }
        return false;
    }

    /**
     * Messages all attendees of a given event. Returns true if event exists and speaker is speaking at the event.
     * @param consoleInput Scanner to use for user input
     * @return boolean value indicating messages being successfully sent
     */
    private boolean messageAllAttendeesOfEvent(Scanner consoleInput) {
        System.out.println("Enter event ID: ");
        String eventID = consoleInput.nextLine();
        List<String> attendeeIDs = em_inst.getEventAttendees(eventID);
        if (attendeeIDs == null) {
            System.out.println("[Error] Event with ID " + eventID + " does not exist.");
            return false;
        }
        if (!em_inst.getEventListBySpeaker(um_inst.getCurrUserID()).contains(em_inst.getEventByID(eventID))) {
            System.out.println("[Error] You do not have permission to message attendees of this event.");
            return false;
        }
        System.out.println("Enter message content: ");
        String message = consoleInput.nextLine();
        if (message.isEmpty()) {
            System.out.println("[Error] Message is empty.");
            return false;
        }
        int recipientCounter = 0;
        for (String recipientID : attendeeIDs) {
            messenger.makeMessage(um_inst.getCurrUserID(), recipientID, message);
            recipientCounter++;
        }
        System.out.println("Message sent successfully to " + recipientCounter + " recipients.");
        return true;
    }

    /**
     * Messages all attendees of events that this speaker is speaking at. Returns true if speaker is speaking at
     * any events and message is not empty.
     * @param consoleInput Scanner for user input
     * @return boolean value indicating messages being succesfully sent
     */
    private boolean messageAllAttendeesOfSpeakerEvents(Scanner consoleInput) {
        List<Event> speakerEvents = em_inst.getEventListBySpeaker(um_inst.getCurrUserID());
        if (speakerEvents.size() == 0) {
            System.out.println("[Error] You are not currently speaking at any events.");
            return false;
        }
        List<String> recipientIDs = new ArrayList<>();
        speakerEvents.forEach(e -> recipientIDs.addAll(e.getAttendees()));
        System.out.println("Enter message content: ");
        String message = consoleInput.nextLine();
        if (message.isEmpty()) {
            System.out.println("[Error] Message is empty");
            return false;
        }
        int recipientCounter = 0;
        for (String recipientID : recipientIDs) {
            messenger.makeMessage(um_inst.getCurrUserID(), recipientID, message);
            recipientCounter++;
        }
        System.out.println("Message sent successfully to " + recipientCounter + "recipients.");
        return true;
    }

    /**
     * View all events user is speaking at.
     * @return true
     */
    private boolean viewSpeakerEvents() {
        for (Event e: em_inst.getEventList()) {
            if(e.getSpeakerID().equals(um_inst.getCurrUserID())) {
                System.out.println(e.getEventID());
            }
        }
        return true;
    }
}
