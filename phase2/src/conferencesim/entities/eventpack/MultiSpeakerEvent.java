package conferencesim.entities.eventpack;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MultiSpeakerEvent extends Event implements Serializable {

    private List<String> speakers = new ArrayList<>();

    public MultiSpeakerEvent(String eventID, String roomID, String organizerID, List<String> speakers,
                             LocalDateTime startTime, LocalDateTime endTime) {
        super(eventID, roomID, organizerID, startTime, endTime);
        this.speakers = speakers;
    }

    public List<String> getSpeakers() {
        return this.speakers;
    }

    public void addSpeaker(String speakerID) {
        this.speakers.add(speakerID);
    }

    public void removeSpeaker(String speakerID) {
        this.speakers.remove(speakerID);
    }
}
