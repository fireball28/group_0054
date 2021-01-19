package conferencesim.entities.userpack;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class Organizer extends User implements Serializable {

    public Organizer(String userID, String password) {
        super(userID, password);
        role = "Organizer";
    }

	public List<String> getPermissions() {
		return Arrays.asList(
				"Event:",
				"addevent <eventID> <location> <startTime \"yyyy-MM-dd HH:mm\")> <endTime \"yyyy-MM-dd HH:mm\"> <speakerIDs* id1,id2,...,id3>",
				"removeevent <eventID>",
				"mostAttended",
//				"longestEvent",
//				"earliestEvent <DayToCheck \"yyyy-MM-dd 00:00\")>",
				"eventWithMostSpeakers",
				"topFiveAttended",
				"usersRegistered",
				"speakersRegistered",
				"viewEvents",
				"viewEventsByLocation <location>",
				"viewEventsByTime <time \"yyyy-MM-dd HH:mm\">",
				"getFellowAttendees",
				"attendevent <eventID> <room>",
				"cancelattend <eventID> <room>",
				"",
				"Registration:",
				"createSpeakerAccount <userID> <password>",
				"createaccount <userID> <password> <role>",
				"",
				"  Rooms:",
				"addroom <roomName> <capacity> <type>",
				"removeroom <roomName>",
				"viewAllRooms",
				"viewRoomTechnologies <roomName>",
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
                "removefriend <userID>",
				"",
                "Requests:",
                "addRequests <requestID> <userID> <type>",
                "changeRequestStatus <requestID> <status>",
                "viewRequestsByUser <userID>",
                "viewRequestsByID <requestID>",
                "viewRequests");
	}
}
