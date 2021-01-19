package conferencesim.entities.userpack;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Attendee extends User implements Serializable {

    public Attendee(String userID, String password) {
        super(userID, password);
        role = "Attendee";
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
				"messageUser <userID> <\"message\">",
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
