package conferencesim.entities.eventpack;

import java.io.Serializable;
import java.util.*;

public class Auditorium extends Room implements Serializable {

    private List<String> technologies = new ArrayList<String>();

    public Auditorium(String roomName, int capacity) {
        super(roomName, capacity);

        technologies.add("Computer");
        technologies.add("Microphone");
        technologies.add("Projector");
    }

    public List<String> getTechnologies(){return technologies;}

}
