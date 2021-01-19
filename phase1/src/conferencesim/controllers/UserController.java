package conferencesim.controllers;

import conferencesim.entities.Event;
import conferencesim.entities.Message;
import conferencesim.usecases.EventManager;
import conferencesim.usecases.Messenger;
import conferencesim.usecases.UserManager;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A class that represents a user controller.
 */
public class UserController implements Commandable {

    UserManager um_inst;
    EventManager em_inst;
    Messenger messenger;

    @Override
    public List<String> getCommandList() {
        return Arrays.asList("messageUser", "attendEvent", "cancelAttend", "viewEvents", "viewEventsByLocation",
                "viewEventsByTime", "getFellowAttendees", "viewFriends", "seeInbox", "seeConversation");
    }

    @Override
    public boolean run(Scanner sc, String command) {
        if (command.equalsIgnoreCase("messageUser")) {
            return messageIndividualUser(sc);
        } else if (command.equalsIgnoreCase("attendEvent")) {
            return signUpForEvent(sc);
        } else if (command.equalsIgnoreCase("cancelAttend")) {
            return cancelSignUp(sc);
        } else if (command.equalsIgnoreCase("viewEventsByLocation")) {
            return viewByLocation(sc);
        } else if (command.equalsIgnoreCase("viewEvents")) {
            return viewEvents();
        } else if (command.equalsIgnoreCase("viewEventsByTime")) {
            return viewByTime();
        } else if (command.equalsIgnoreCase("viewEventsBySpeaker")) {
            return viewBySpeaker(sc);
        } else if (command.equalsIgnoreCase("getFellowAttendees")) {
            return grabFellowAttendees();
        } else if (command.equalsIgnoreCase("viewFriends")) {
            return viewFriends();
        } else if (command.equalsIgnoreCase("seeInbox")) {
            return seeAllMessages();
        } else if (command.equalsIgnoreCase("seeConversation")) {
            return seeConversation(sc);
        }
        return false;
    }

    /**
     * Creates a user controller.
     * @param um_inst instance of user manager
     * @param em_inst instance of event manager
     * @param messenger instance of messenger
     */
    public UserController(UserManager um_inst, EventManager em_inst, Messenger messenger) {
        this.um_inst = um_inst;
        this.em_inst = em_inst;
        this.messenger = messenger;
    }

    /**
     * Send a message to a specific user
     * @param userInput Scanner for user input
     * @return true if message was sent successfully, false if user does not exist or message is empty
     */
    private boolean messageIndividualUser(Scanner userInput) {
        System.out.println("Enter recipient ID: ");
        String toID = userInput.nextLine();
        if (!um_inst.idExists(toID)) {
            System.out.println("Such user does not exist.");
            return false;
        }
        System.out.println("Enter your message: ");
        String message = userInput.nextLine();
        if (message.isEmpty()) {
            System.out.println("Message body is empty.");
            return false;
        }
        messenger.makeMessage(um_inst.getCurrUserID(), toID, message);
        System.out.println("Message sent to " + toID + " successfully.");
        return true;
    }

    /**
     * Sign up to attend an event
     * @param userInput Scanner for user input
     * @return true if event exists and event sign up was successful, false if event does not exist or user is
     * already attending event
     */
    private boolean signUpForEvent(Scanner userInput) {
        System.out.println("Enter event ID: ");
        String eventID = userInput.nextLine();
        if (!em_inst.eventExists(eventID)) {
            System.out.println("Such event does not exist.");
            return false;
        }
        Event event = em_inst.getEventByID(eventID);
        if (um_inst.userEventSignUp(um_inst.getCurrUserID(), event)) {
            for (String attendee: event.getAttendees()){
                um_inst.addUserFriend(um_inst.getCurrUserID(), attendee);
            }
            System.out.println("Successfully signed up for event. EventID: " + eventID);
            return true;
        }
        System.out.println("You have already signed up for this event.");
        return false;
    }

    /**
     * Remove current user from event attendee list.
     * @param consoleInput Scanner for user input
     * @return true if user is attending event and cancellation was successful, false otherwise
     */
    private boolean cancelSignUp(Scanner consoleInput) {
        System.out.println("Enter eventID: ");
        String eventID = consoleInput.nextLine();
        while (!em_inst.eventExists(eventID)) {
            System.out.println("No such event exists try again: ");
            eventID = consoleInput.nextLine();
        }
        if (!em_inst.getEventByID(eventID).getAttendees().contains(um_inst.getCurrUserID())) {
            System.out.println("You are not attending this event...");
            return false;
        } else {
            for (String attendee: em_inst.getEventByID(eventID).getAttendees()) {
                um_inst.deleteUserFriend(um_inst.getCurrUserID(), attendee);
            }
            return um_inst.userEventCancel(um_inst.getCurrUserID(), em_inst.getEventByID(eventID));
        }
    }

    /**
     * View all scheduled events.
     * @return true
     */
    private boolean viewEvents() {
        for(Event e: em_inst.getEventList()) {
            String s = e.getAttendees().contains(um_inst.getCurrUserID()) ? e + " Attending" : e.toString();
            System.out.println(s);
        }
        return true;
    }

    /**
     * View events by location.
     * @return true if location exists, false otherwise
     */
    private boolean viewByLocation(Scanner consoleInput) {
        System.out.println("Enter location: ");
        String location = consoleInput.nextLine();
        if (!em_inst.getRooms().contains(location)) {
            System.out.println("Not a valid location.");
            return false;
        }
        for (Event e : em_inst.getEventListByLocation(location)) {
            System.out.println(e);
        }
        return true;
    }

    /**
     * View events by time.
     * @return true if events are scheduled, false otherwise
     */
    private boolean viewByTime() {
        if(em_inst.getEventList().size() == 0) {
            System.out.println("There are no scheduled events");
            return false;
        }
        for(Event e: em_inst.getEventList()) {
            System.out.println(e.getTime() + e.getEventID());
        }
        return true;
    }

    /**
     * View events by speaker.
     * @param consoleInput Scanner for user input
     * @return true if speaker exists and is giving a talk, false otherwise
     */
    private boolean viewBySpeaker(Scanner consoleInput) {
        System.out.println("Enter SpeakerID: ");
        String speakerID = consoleInput.nextLine();
        if (em_inst.getEventListBySpeaker(speakerID).size() == 0) {
            System.out.println("Speaker not found");
            return false;
        }
        for (Event e: em_inst.getEventList()) {
            if(e.getSpeakerID().equals(speakerID)) {
                System.out.println(e.getSpeakerID() + e.getEventID());
            }
        }
        return true;
    }

    /**
     * View attendees attending same events as this user
     * @return true if user is attending any events, false if user has not yet signed up for an event
     */
    private boolean grabFellowAttendees() {
        String currUser = um_inst.getCurrUserID();
        if (em_inst.getAttendeeEvents(currUser).size() == 0) {
            System.out.println("\n");
            return false;
        }
        for (Event e: em_inst.getAttendeeEvents(currUser)) {
            String s = e.getAttendees().stream().filter(a -> !a.equals(currUser))
                    .collect(Collectors.joining(", ")) + " " + e.getEventID();
            System.out.println(s);
        }
        return true;
    }

    /**
     * View all friends in user's friend list
     * @return true if user has friends, false otherwise
     */
    private boolean viewFriends() {
        if (um_inst.getCurrUser().getFriends().size() == 0) {
            return false;
        }
        for (String f: um_inst.getCurrUser().getFriends()) {
            System.out.println(f);
        }
        return true;
    }

    /**
     * See all messages received in user's inbox
     * @return true
     */
    private boolean seeAllMessages() {
        String currUser = um_inst.getCurrUserID();
        if (messenger.getMessagesByRecipient(currUser).size() == 0) {
            System.out.println("No messages received");
            return true;
        }
        for (Message m: messenger.getMessagesByRecipient(currUser)) {
            System.out.println(m.toString());
        }
        return true;
    }

    /**
     * View conversation between current user and userID
     * @param consoleInput Scanner for user input
     * @return boolean indicating whether conversation exists
     */
    private boolean seeConversation(Scanner consoleInput) {
        System.out.println("Enter userID to see your conversation with them: ");
        String senderID = consoleInput.nextLine();
        if (!um_inst.idExists(senderID)) {
            System.out.println("Such a user does not exist");
            return false;
        }

        List<Message> conversation = new ArrayList<>();
        for (Message m: messenger.getMessagesByRecipient(um_inst.getCurrUserID())) {
            if (m.getSenderUserID().equals(senderID)) {
                conversation.add(m);
            }
        }
        for (Message m: messenger.getMessageBySender(um_inst.getCurrUserID())) {
            if (m.getReceiverUserID().equals(senderID)) {
                conversation.add(m);
            }
        }
        if (conversation.size() == 0) {
            System.out.println("You did not start a conversation with this user");
            return false;
        }

        for (Message m: conversation.stream().sorted().collect(Collectors.toList())) {
            String s = m.getSenderUserID() + "\n" + m.getSendTimeString() + m.getContent();
            System.out.println(s);
        }
        return true;
    }
}
