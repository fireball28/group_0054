package conferencesim.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message implements Comparable<Message>, Serializable {

    private String messageID;
    private String senderUserID;
    private String receiverUserID;
    private String content;
    //private LocalDateTime sendTime;
    private String sendTime;
    //private DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    // for phase 2, add the repliedTo boolean to make it possible to sort by unread (adding feature)
    private boolean unread;

    /**
     * We require the senderUserID, receiverUserID and content of the message in order to create an instance of Message
     * @param senderUserID user id of the sender
     * @param receiverUserID user id of the recipient
     * @param content content of the message
     */
    public Message(String senderUserID, String receiverUserID, String content){
        this.messageID = senderUserID + receiverUserID + LocalDateTime.now().toString();
        this.senderUserID = senderUserID;
        this.receiverUserID = receiverUserID;
        this.content = content;
        this.sendTime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        this.unread = true;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getSenderUserID(){
        return senderUserID;
    }

    public String getReceiverUserID(){
        return receiverUserID;
    }

    public String getContent(){
        return content;
    }

    public boolean getunread() {return this.unread;}

    public String toString() {
        return "[" + getSendTimeString() + "] \"" + this.getContent() + "\" from " + this.getSenderUserID();
    }

    public LocalDateTime getSendTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(sendTime, formatter);
        return dateTime;
    }

    public String getSendTimeString() {
        return sendTime;
    }

    @Override
    public int compareTo(Message o) {
        // return <0 if this is supposed to be "less than" o.
        // return 0 if this is supposed to be the "same as" o.
        // return 0> if this is supposed to be "greater than" o.
        return this.getSendTime().compareTo(o.getSendTime());
    }

    // Not coding setters as the variables will be set by the constructor when a new conferencesim.entities.Message is sent
    // i.e. when the method to send messages (Prob in the Messeger class) creates a new message object

}