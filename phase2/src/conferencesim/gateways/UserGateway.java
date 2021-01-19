package conferencesim.gateways;

import conferencesim.usecases.UserManager;
import java.io.*;

public class UserGateway extends MasterGateway implements iGateway{

    /**
     * Takes an instance of UserManager and saves it as a .ser file in the serfiles directory
     * @param save the type of ManagerType to save it as
     * @param args the instance of UserManager to be saved
     */

    @Override
    public void saveFile(ManagerType save, Object[] args) {
        if (save == ManagerType.USERMANAGER) {
            File file = new File("UserManagerInfo.txt");
            saveHelper(file, args[0]);
        }
    }

    /**
     * Loads a previously saved UserManager instance from the serfiles directory
     * If no saved instance exists, it creates a new instance of UserManager and returns that
     * @return an instance of UserManager
     */

    public UserManager loadFile(){
        try{
            FileInputStream fis = new FileInputStream("UserManagerInfo.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            UserManager um = (UserManager)ois.readObject();
            ois.close();
            fis.close();
            return um;

        } catch (IOException | ClassNotFoundException io) {

            return new UserManager();
        }
    }


}
