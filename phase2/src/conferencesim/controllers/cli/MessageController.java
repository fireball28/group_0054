package conferencesim.controllers.cli;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import conferencesim.usecases.EventManager;
import conferencesim.usecases.Messenger;
import conferencesim.usecases.UserManager;

public class MessageController extends CommandController {

	public MessageController(UserManager um, EventManager em, Messenger m) {
		this.um_inst = um;
		this.em_inst = em;
		this.m_inst = m;
	}
	
	public void messageUser(String userID, String message) {
		m_inst.makeMessage(um_inst.getCurrUserID(), userID, message);
	}
	
	public void messageAllAttendees(String message) {
    	for (String recipientID : um_inst.getUserIDList()) {
    		m_inst.makeMessage(um_inst.getCurrUserID(), recipientID, message);
    	}
    }
	
	public void messageAllSpeakers(String message) {
    	for (String recipientID : um_inst.getAllSpeakerIDs()) {
    		m_inst.makeMessage(um_inst.getCurrUserID(), recipientID, message);
    	}
    }
	
	public void messageAllAttendeesOfEvent(String eventID, String message) {
		for (String recipientID : em_inst.getEventAttendees(eventID)) {
			m_inst.makeMessage(um_inst.getCurrUserID(), recipientID, message);
		}
	}

	public boolean removeMessage(String messageID) {
		return m_inst.deleteMessage(m_inst.getMessageByID(messageID));
	}

	public void removeAllMessagesInbox() {
		m_inst.deleteAllMessagesInbox();
	}

	public void removeAllMessagesMessageList() {
		 m_inst.deleteAllMessagesMessageList();
	}

	public boolean addDeletedMessage(String messageID) {
		return m_inst.retrieveDeletedMessage(m_inst.getMessageByID(messageID));
	}

	public boolean arMessage(String messageID) {
		return m_inst.archiveMessage(m_inst.getMessageByID((messageID)));
	}

	public void arAllMessage() {
		 m_inst.archiveAllMessages();
	}

	public boolean unarMessage(String messageID) {
		return m_inst.unarchiveMessage(m_inst.getMessageByID(messageID));
	}

	public boolean markMsgUnread(String messageID) { return m_inst.unreadMessage(m_inst.getMessageByID(messageID));}

	public boolean removeMessageFromTrash(String messageID) {
		return m_inst.permanentlyDeleteMessage(m_inst.getMessageByID(messageID));
	}

	public void removeAllMessageFromTrash() {
		 m_inst.permanentlyDeleteAllMessages();
	}

	public void seeInbox() {
		m_inst.getInbox(um_inst.getCurrUserID());
	}

	public void seeTrash() {
		m_inst.getTrash(um_inst.getCurrUserID());
	}

	public void seeAllMessages() {m_inst.getAllMessages(um_inst.getCurrUserID());}
	
	public void seeConversation(String userID) {
		m_inst.getConversation(um_inst.getCurrUserID(), userID);
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
			System.out.println("Bad command");
			exc.printStackTrace();
		}
	}
}
