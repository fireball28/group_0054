package conferencesim.entities.eventpack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Classroom extends Room implements Serializable {

    private List<String> technologies = new ArrayList<String>();

    public Classroom(String roomName, int capacity) {
        super(roomName, capacity);

        technologies.add("Computer");
        technologies.add("Desks");
        technologies.add("Whiteboard");
    }

    public List<String> getTechnologies(){return technologies;}

}
