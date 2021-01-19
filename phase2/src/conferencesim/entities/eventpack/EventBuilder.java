package conferencesim.entities.eventpack;


import java.time.LocalDateTime;
import java.util.List;

public class EventBuilder {

    private String eventID;
    private String location;
    private String organizerID;
    private LocalDateTime startTime, endTime;
    private String speaker = null;
    private List<String> speakers = null;

    public EventBuilder setEventID(String eventID) {
        this.eventID = eventID;
        return this;
    }

    public EventBuilder setLocation(String roomName) {
        this.location = roomName;
        return this;
    }

    public EventBuilder setOrganizer(String organizerID) {
        this.organizerID = organizerID;
        return this;
    }

    public EventBuilder setTime(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        return this;
    }

    public EventBuilder setSpeaker(String speakerID) {
        this.speaker = speakerID;
        return this;
    }

    public EventBuilder setSpeakers(List<String> panel) {
        this.speakers = panel;
        return this;
    }

    public Event build() {
        if (this.speaker != null) {
            return new Talk(this.eventID, this.location, this.organizerID, this.speaker, this.startTime, this.endTime);
        } else if (this.speakers != null) {
            return new MultiSpeakerEvent(this.eventID, this.location, this.organizerID, this.speakers,
                    this.startTime, this.endTime);
        } else {
            return new Event(this.eventID, this.location, this.organizerID, this.startTime, this.endTime);
        }
    }
}
