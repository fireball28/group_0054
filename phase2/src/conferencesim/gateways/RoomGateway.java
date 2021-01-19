package conferencesim.gateways;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import conferencesim.usecases.RoomManager;

public class RoomGateway extends MasterGateway implements iGateway {

    /**
     * Takes an instance of RoomManager and saves it as a .ser file in the serfiles directory
     * @param save the type of ManagerType to save it as
     * @param args the instance of RoomManager to be saved
     */

    @Override
    public void saveFile(ManagerType save, Object[] args) {
        if (save == ManagerType.ROOMMANAGER) {
            File file = new File("RoomManagerInfo.txt");
            saveHelper(file, args[0]);
        }
    }

    /**
     * Loads a previously saved RoomManager instance from the serfiles directory
     * If no saved instance exists, it creates a new instance of RoomManager and returns that
     * @return an instance of RoomManager
     */

    public RoomManager loadFile(){
        try{
            FileInputStream fis = new FileInputStream("RoomManagerInfo.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            RoomManager rm = (RoomManager)ois.readObject();
            ois.close();
            fis.close();
            return rm;

        } catch (IOException | ClassNotFoundException io) {

            return new RoomManager();
        }
    }
}
