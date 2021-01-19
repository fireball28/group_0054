package conferencesim.gateways;

import conferencesim.usecases.EventManager;
import conferencesim.entities.Event;
import sun.text.resources.is.FormatData_is;

import java.lang.reflect.Array;
import java.time.LocalDateTime;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EventGateway {

    public void saveEvents(EventManager save){
        try{
            File file = new File("EventManagerInfo.txt");
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(save);
            oos.close();
            fos.close();
        }
        catch (IOException io) {
            io.printStackTrace();
        }
    }

    public EventManager loadEvents() throws ClassNotFoundException{
        try{
            FileInputStream fis = new FileInputStream("EventManagerInfo.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            EventManager em = (EventManager)ois.readObject();
            ois.close();
            fis.close();
            return em;
        } catch (IOException io) {

            return new EventManager();
        }
    }
}