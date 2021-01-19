package conferencesim.entities.eventpack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Court extends Room implements Serializable {

    private List<String> technologies = new ArrayList<String>();

    public Court(String roomName, int capacity) {
        super(roomName, capacity);

        technologies.add("Chairs");
        technologies.add("Tents");
        technologies.add("Foodtruck");
    }

    public List<String> getTechnologies(){return technologies;}

}
