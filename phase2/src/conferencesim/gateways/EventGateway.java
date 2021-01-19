package conferencesim.gateways;

import conferencesim.usecases.EventManager;
import java.io.*;

public class EventGateway extends MasterGateway implements iGateway {

    /**
     * Takes an instance of EventManager and saves it as a .ser file in the serfiles directory
     * @param save the type of ManagerType to save it as
     * @param args the instance of EventManager to be saved
     */

    @Override
    public void saveFile(ManagerType save, Object[] args) {
        if (save == ManagerType.EVENTMANAGER) {
            File file = new File("EventManagerInfo.txt");
            saveHelper(file, args[0]);
        }
    }

    /**
     * Loads a previously saved EventManager instance from the serfiles directory
     * If no saved instance exists, it creates a new instance of EventManager and returns that
     *
     * @return an instance of EventManager
     */


    @Override
    public EventManager loadFile() {
        try{
            FileInputStream fis = new FileInputStream("EventManagerInfo.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            EventManager em = (EventManager)ois.readObject();
            ois.close();
            fis.close();
            return em;
        } catch (IOException | ClassNotFoundException io) {

            return new EventManager();
        }
    }
}
