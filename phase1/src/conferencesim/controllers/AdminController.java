package conferencesim.controllers;

import conferencesim.usecases.EventManager;
import conferencesim.usecases.Messenger;
import conferencesim.usecases.UserManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * A class that represents an admin controller.
 */
public class AdminController implements Commandable {

    private UserManager um_inst;
    private EventManager em_inst;

    private Messenger messenger;

    public AdminController(UserManager um_inst, EventManager em_inst, Messenger messenger) {
        this.um_inst = um_inst;
        this.em_inst = em_inst;

        this.messenger = messenger;
    }

    @Override
    public List<String> getCommandList() {
        return Arrays.asList("addevent", "messageallattendees", "createSpeakerAccount", "deleteevent", "addroom", "removeroom",
                "messageallspeakers", "getAllUserListTest");
    }

    @Override
    public boolean run(Scanner sc, String command) {
        if (command.equalsIgnoreCase("addevent")) {
            return addEvent(sc);
        } else if (command.equalsIgnoreCase("createspeakeraccount")) {
            return createSpeakerAccount(sc);
        } else if (command.equalsIgnoreCase("deleteevent")) {
            return deleteEvent(sc);
        } else if (command.equalsIgnoreCase("addroom")) {
            return addRoom(sc);
        } else if (command.equalsIgnoreCase("removeroom")) {
            return removeRoom(sc);
        } else if (command.equalsIgnoreCase("messageallspeakers")) {
            return messageAllSpeakers(sc);
        } else if (command.equalsIgnoreCase("getalluserlisttest")) {
            return getAllUserListTest();
        }
        return false;
    }

    /**
     * Adds a room to the stored event controller if the room does not already exist.
     * @param consoleInput Scanner to use for user input
     * @return boolean value indicating successful room addition
     */
    private boolean addRoom(Scanner consoleInput) {
        System.out.println("Enter room name: ");
        String roomName = consoleInput.nextLine();
        if (em_inst.addRoom(roomName)) {
            System.out.println("Room " + roomName + " added successfully.");
            return true;
        } else {
            System.out.println("Room " + roomName + " already exists, adding room not successful.");
            return false;
        }
    }

    /**
     * Removes a room from the stored event controller if the room exists.
     * @param consoleInput Scanner to use for user input
     * @return boolean value indicating successful room removal
     */
    private boolean removeRoom(Scanner consoleInput) {
        System.out.println("Enter room for removal: ");
        String roomName = consoleInput.nextLine();
        if (em_inst.removeRoom(roomName)) {
            System.out.println("Room " + roomName + " removed successfully.");
            return true;
        } else {
            System.out.println("Room " + roomName + " does not exist, removing room not successful.");
            return false;
        }
    }

    /**
     * Adds an event. Returns true if the event does not already exist, and if there are no time conflicts with existing events.
     * At least one speaker must be given for successful creation.
     * @param consoleInput Scanner to use for user input
     * @return boolean value indicating successful event addition
     */
    private boolean addEvent(Scanner consoleInput) {
        System.out.println("Enter ID for new event: ");
        String eventID = consoleInput.nextLine();
        if (em_inst.eventExists(eventID)) {
            System.out.println("[Error] Event with ID " + eventID + " already exists.");
            return false;
        }
        System.out.println("Enter time of event in yyyy-MM-dd HH:mm format: ");
        String rawTime = consoleInput.nextLine();
        LocalDateTime time;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            time = LocalDateTime.parse(rawTime, formatter);
        } catch (DateTimeParseException exc) {
            System.out.println("[Error] Time was not given in the correct format (yyyy-MM-dd HH:mm");
            return false;
        }
        if (em_inst.eventCoincides(time)) {
            System.out.println("[Warning] An event is already scheduled within an hour of that time.\n");
        }
        System.out.println("Enter location of event: ");
        String location = consoleInput.nextLine();

        System.out.println("Enter speakerID of event speaker: ");
        String speakerID = consoleInput.nextLine();

        if (!um_inst.getUserIDList().contains(speakerID)) {
            System.out.println("Speaker does not exist in the program... \ncreate Speaker account first");
            return false;
        }

        if (!em_inst.addEvent(time, location, eventID, um_inst.getCurrUserID(), speakerID)) {
            System.out.println("[Error] Failed to add event!");
            return false;
        }
        System.out.println("Event added successfully!");
        return true;
    }

    /**
     * Removes an event. Returns true if event exists.
     * @param consoleInput Scanner to use for user input
     * @return boolean value indicating successful removal
     */
    private boolean deleteEvent(Scanner consoleInput) {
        System.out.println("Enter eventID: ");
        String eventID = consoleInput.nextLine();
        if (em_inst.deleteEvents(em_inst.getEventByID(eventID))) {
            return true;
        } else {
            System.out.println("No such event is scheduled.");
            return false;
        }
    }

    /**
     * Creates a speaker account.
     * @param consoleInput Scanner to use for user input
     * @return boolean value indicating successful account creation
     */
    private boolean createSpeakerAccount(Scanner consoleInput) {
        System.out.println("Enter speaker name: ");
        String speakerID = consoleInput.nextLine();
        //should be random password, but for the purpose of phase 1 we don't need this function yet. Consider for phase2
        String password = "1234";
        return um_inst.registerUser(speakerID, password, "Speaker");
    }

    /**
     * Messages all registered speakers. Returns true if message is not empty.
     * @param consoleInput Scanner to use for user input
     * @return boolean value indicating messages being successfully sent
     */
    private boolean messageAllSpeakers(Scanner consoleInput) {
        System.out.println("Enter message content: ");
        String message = consoleInput.nextLine();
        if (message.isEmpty()) {
            System.out.println("[Error] Message is empty.");
            return false;
        }
        int recipientCounter = 0;
        for (String s: um_inst.getAllSpeakerIDs()) {
            messenger.makeMessage(um_inst.getCurrUserID(), s, message);
            recipientCounter++;
        }
        System.out.println("Message sent successfully to " + recipientCounter + " speakers.");
        return true;
    }

    private boolean getAllUserListTest() {
        List<String> result = um_inst.getUserIDList();
        for (String i : result) {
            System.out.println(i);
        }
        return true;
    }
}
