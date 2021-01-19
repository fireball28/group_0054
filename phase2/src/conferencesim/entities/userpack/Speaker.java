package conferencesim.entities.userpack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Speaker extends User implements Serializable {

    private List<String> talks = new ArrayList<String>();

    public Speaker(String userID, String password) {
        super(userID, password);
        role = "Speaker";
    }

    public void addSpeakerTalks(String eventID){
        talks.add(eventID);
    }

    public List<String> getEvents(){
        return talks;
    }


    public List<String> getPermissions() {
        return Arrays.asList(
                "Events:",
                "attendevent <eventID> <room>",
                "cancelattend <eventID> <room>",
                "viewEvents",
                "viewEventsByLocation <location>",
                "viewEventsByTime <time \"yyyy-MM-dd HH:mm\">",
                "getFellowAttendees",
                "",
                "Message:",
                "messageallattendeesofevent <eventID> <message>",
        		"viewSpeakerEvents",
        		"messageuser <userID> <\"message\">",
                "removeMessage <messageID>",
                "removeAllMessagesInbox",
                "removeAllMessagesMessageList",
                "addDeletedMessage <messageID>",
                "arMessage <messageID>",
                "arAllMessage",
                "unarMessage <messageID>",
                "removeMessageFromTrash <messageID>",
                "removeAllMessageFromTrash",
                "seeAllMessages",
                "seeTrash",
                "markMsgUnread <messageID>",
                "",
                "Friends:",
                "viewfriends",
                "numFriends",
                "seeinbox", 
                "seeconversation <userID>",
                "addfriend <userID>",
                "removefriend <userID>");
    }
}
