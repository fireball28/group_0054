package conferencesim.entities.userpack;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Admin extends User implements Serializable {
	
    public Admin(String userID, String password) {
        super(userID, password);
        role = "Admin";
    }

	public List<String> getPermissions() {
		return Arrays.asList(
				"Events:",
				"addevent <eventID> <location> <startTime \"yyyy-MM-dd HH:mm\")> <endTime \"yyyy-MM-dd HH:mm\"> <speakerIDs* id1,id2,...,id3>",
				"removeevent <eventID>",
				 "mostAttended",
//				"longestEvent",
//				"earliestEvent <DayToCheck \"yyyy-MM-dd 00:00\")>",
				"eventWithMostSpeakers",
				"topFiveAttended",
				"attendevent <eventID> <room>",
				"cancelattend <eventID> <room>",
				"viewEvents",
				"viewEventsByLocation <location>",
				"viewEventsByTime <time \"yyyy-MM-dd HH:mm\">",
				"getFellowAttendees",
				"",
				"Registration:",
				"createaccount <userID> <password> <role>",
				"usersRegistered",
				"speakersRegistered",
				"createSpeakerAccount <userID> <password>",
				"",
				"Rooms:",
				"addroom <roomName> <capacity> <type>",
				"viewAllRooms",
				"viewRoomTechnologies <roomName>",
				"removeroom <roomName>",
				"",
				"Message:",
				"messageallattendees <message>",
				"messageallspeakers <message>",
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
				"removefriend <userID>"
				);
	}
}
