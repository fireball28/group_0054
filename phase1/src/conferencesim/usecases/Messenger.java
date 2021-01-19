package conferencesim.usecases;

import conferencesim.entities.Message;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Messenger implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Message> messageList;

    /**
     * Creates an empty Messenger
     */
    public Messenger() {
        this.messageList = new ArrayList<>();
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
    }

    private List<Message> getMessageList() {
        return this.messageList;
    }

    /**
     * Delete a message from the messenger. Return true if the messenger contains the message and the message is removed
     * from the messenger else, return false.
     * @param m message to be deleted.
     * @return true if the messenger contains the message and the message is removed from the messenger else, return
     * false
     */
    public boolean deleteMessage(Message m) {
        if(this.messageList.contains(m)) {
            this.messageList.remove(m);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a list of messages received by the recipient that is appended individually if the message in message list
     * has user id equal to the recipient id.
     * @param recipID id of the recipient.
     * @return a list of messages received by the recipient that is appended individually if the message in message list
     * has user id equal to the recipient id.
     */
    public List<Message> getMessagesByRecipient(String recipID) {
        List<Message> messagesByRecipient = new ArrayList<>();
        for (Message m: this.getMessageList()) {
            if (m.getReceiverUserID().equals(recipID)) {
                messagesByRecipient.add(m);
            }
        }
        return messagesByRecipient;
    }

    /**
     * Returns a list of messages sent by the sender that is appended individually if the message in message list
     * has user id equal to the sender id.
     * @param senderID id of the sender.
     * @return a list of messages sent by the sender that is appended individually if the message in message list has
     * user id equal to the sender id.
     */
    public List<Message> getMessageBySender(String senderID) {
        List<Message> messagesBySender = new ArrayList<>();
        for (Message m: this.getMessageList()) {
            if (m.getSenderUserID().equals(senderID)) {
                messagesBySender.add(m);
            }
        }
        return messagesBySender;
    }
}
