package conferencesim.usecases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import conferencesim.entities.Message;

public class Messenger extends Usecase implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Message> messageList;
    private List<Message> inboxList;
    private List<Message> trashList;

    /**
     * Creates an empty Messenger
     */
    public Messenger() {
        this.messageList = new ArrayList<>();
        this.inboxList = new ArrayList<>();
        this.trashList = new ArrayList<>();
    }

    /**
     * Creates a message that is sent and received from a sender to a recipient.
     * @param sender of the message.
     * @param receiver of the message.
     * @param content of the message.
     */
    public void makeMessage(String sender, String receiver, String content) {
        Message m =  new Message(sender, receiver, content);
        this.messageList.add(m);
        this.inboxList.add(m);
        view.callView().print("Successfully messaged " + receiver + ". MessageID is " + m.getMessageID());
    }

    private List<Message> getMessageList() {
        return this.messageList;
    }

    private List<Message> getInboxList() {
        return this.inboxList;
    }

    private List<Message> getTrashList() {return this.trashList; }

    public Message getMessageByID(String messageID) {
        for (Message m: this.getMessageList()) {
            if (m.getMessageID().equals(messageID)) {
                return m;
            }
        }
        eView.callView().messageDoesNotExist();
        return null;
    }

    /**
     * Delete a message from both inbox and all messages. Return true if the messenger contains the message and the
     * message is removed from the messenger else, return false.
     * @param m message to be deleted.
     * @return true if the messenger contains the message and the message is removed from the messenger else, return
     * false
     */
    public boolean deleteMessage(Message m) {
        if (this.messageList.contains(m) & this.inboxList.contains(m)) {
            this.messageList.remove(m);
            this.inboxList.remove(m);
            this.trashList.add(m);
            view.callView().print("Successful!");
            return true;
        }
        else if (this.messageList.contains(m) & !this.inboxList.contains(m)) {
            this.messageList.remove(m);
            this.trashList.add(m);
            view.callView().print("Successful!");
            return true;
        }
        else {
            view.callView().print("Unsuccessful!");
            return false;
        }
    }

    /**
     * Delete all messages from the inbox list by moving them into the trash.
     */
    public void deleteAllMessagesInbox() {
        this.trashList.addAll(inboxList);
        for (Message m : inboxList) {
            messageList.remove(m);
        }

        this.inboxList.clear();
        view.callView().print("Successful!");
    }

    /**
     * Delete all messages from both the message list and inbox list by moving them into the trash.
     */
    public void deleteAllMessagesMessageList() {
        this.trashList.addAll(messageList);
        this.messageList.clear();
        this.inboxList.clear();
        view.callView().print("Successful!");
    }

    /**
     * Retrieve a deleted message from the trash list back into the inboxList and messageList and return true. Else,
     * return false
     * @param m message to be retrieved
     * @return true if a deleted message from the trash list is retrieved into the inboxList and messageList else,
     * return false
     */
    public boolean retrieveDeletedMessage(Message m) {
        if (this.trashList.contains(m) & !this.inboxList.contains(m) & !this.messageList.contains(m)) {
            this.trashList.remove(m);
            this.inboxList.add(m);
            this.messageList.add(m);
            Collections.sort(inboxList);
            Collections.sort(messageList);
            view.callView().print("Successfully retrieved");
            return true;
        } else {
            view.callView().print("Unsuccessful!");
            return false;
        }
    }

    /**
     * Archive a message by deleting a message only from the inbox list. If archived, return true, else false.
     * @param m message to be archived
     * @return true if message is archived, else return false.
     */
    public boolean archiveMessage(Message m) {
        if (this.inboxList.contains(m)) {
            this.inboxList.remove(m);
            view.callView().print("Successful!");
            return true;
        } else {
            view.callView().print("Unsuccessful!");
            return false;
        }
    }

    /**
     * Mark a message unread by moving out of the messageList and back to the inbox
     * @param m message to be unread
     * @return true if message is marked unread, else return false.
     */
    public boolean unreadMessage(Message m) {
        if (this.messageList.contains(m) & !this.inboxList.contains(m)) {
            this.messageList.remove(m);
            this.inboxList.add(m);
            view.callView().print("Successful!");
            return true;
        } else {
            view.callView().print("Unsuccessful!");
            return false;
        }
    }

    /**
     * Archive all messages in the inboxList.
     */
    public void archiveAllMessages() {
        this.inboxList.clear();
        view.callView().print("Successful!");
    }

    /**
     * Return true if the message can be unarchived. Else return false.
     * @param m message to be unarchived
     * @return true if the message can be unarchived. Else return false.
     */
    public boolean unarchiveMessage(Message m) {
        if (this.messageList.contains(m) & !this.inboxList.contains(m)) {
            this.inboxList.add(m);
            view.callView().print("Successful!");
            return true;
        } else {
            view.callView().print("Unsuccessful!");
            return false;
        }
    }

    /**
     * If the trash list contains the message, m, to be deleted, remove the message from the trash list and return true
     * else, return false.
     * @param m message to be permanantly deleted
     * @return true if message is removed from trash list else, return false.
     */
    public boolean permanentlyDeleteMessage(Message m) {
        if (this.trashList.contains(m)) {
            this.trashList.remove(m);
            view.callView().print("Successful!");
            return true;
        } else {
            view.callView().print("Unsuccessful!");
            return false;
        }
    }

    /**
     * Permanantly deletes all messages from the trash.
     */
    public void permanentlyDeleteAllMessages() {
        this.trashList.clear();
    }

    private List<Message> getAllMessagesByRecipient(String recipID) {
        List<Message> messagesByRecipient = new ArrayList<>();
        for (Message m: this.getMessageList()) {
            if (m.getReceiverUserID().equals(recipID)) {
                messagesByRecipient.add(m);
            }
        }
        view.callView().print("Successful!");
        return messagesByRecipient;
    }

    public void getAllMessages(String userID) {

        if(this.getAllMessagesByRecipient(userID).size()==0) {
            eView.callView().noMessagesMessageList();
        }
        for (Message m: this.getAllMessagesByRecipient(userID)) {
            view.print(m.toString());
        }
    }

    /**
     * Returns a list of messages received by the recipient that is appended individually if the message in message list
     * has user id equal to the recipient id.
     * @param recipID id of the recipient.
     * @return a list of messages received by the recipient that is appended individually if the message in message list
     * has user id equal to the recipient id.
     */
    private List<Message> getInboxMessagesByRecipient(String recipID) {
        List<Message> inboxMessagesByRecipient = new ArrayList<>();

        for (Message m: this.getInboxList()) {
            if (m.getReceiverUserID().equals(recipID)) {
                inboxMessagesByRecipient.add(m);
            }
        }

        return inboxMessagesByRecipient;
    }

    public void getInbox(String userID) {
        if (this.getInboxMessagesByRecipient(userID).size()==0) {
            eView.callView().noMessagesInbox();
        }
        for (Message m: this.getInboxMessagesByRecipient(userID)) {
            view.print(m.toString());
        }
    }

    private List<Message> getTrashMessagesByRecipient(String recipID) {
        List<Message> trashByRecipient = new ArrayList<>();
        for (Message m: this.getTrashList()) {
            if (m.getReceiverUserID().equals(recipID)) {
                trashByRecipient.add(m);
            }
        }

        return trashByRecipient;
    }

    public void getTrash(String userID) {

        if(this.getTrashMessagesByRecipient(userID).size()==0) {
            eView.callView().noMessagesTrash();
        }
        for (Message m: this.getTrashMessagesByRecipient(userID)) {
            view.print(m.toString());
        }
    }

    /**
     * Returns a list of messages sent by the sender that is appended individually if the message in message list
     * has user id equal to the sender id.
     * @param senderID id of the sender.
     * @return a list of messages sent by the sender that is appended individually if the message in message list has
     * user id equal to the sender id.
     */
    public List<Message> getMessageBySender(String senderID) {
        List<Message> messagesBySender = this.getMessageBySenderNoPrint(senderID);
        for (Message m: messagesBySender) {
            view.print(m.toString());
        }
        return messagesBySender;
    }

    private List<Message> getMessageBySenderNoPrint(String senderID){
        List<Message> messagesBySender = new ArrayList<>();
        for (Message m: this.getMessageList()) {
            if (m.getSenderUserID().equals(senderID)) {
                messagesBySender.add(m);
            }
        }
        return messagesBySender;
    }

    public void getConversation(String senderID, String recipID) {
        for (Message m: this.getMessageBySenderNoPrint(senderID)) {
            if (m.getReceiverUserID().equals(recipID)) {
                view.print(m.toString());
            }
        }
    }
}
