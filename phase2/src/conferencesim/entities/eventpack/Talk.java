package conferencesim.entities.eventpack;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Talk extends Event implements Serializable {

    private String speakerID;

    public Talk(String eventID, String roomID, String organizerID, String speakerID, LocalDateTime startTime, LocalDateTime endTime) {
        super(eventID, roomID, organizerID, startTime, endTime);
        this.speakerID = speakerID;
    }

    public String getSpeakerID() {
        return this.speakerID;
    }

    public void setSpeakerID(String speakerID) {
        this.speakerID = speakerID;
    }
}
