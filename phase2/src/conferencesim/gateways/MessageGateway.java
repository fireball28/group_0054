package conferencesim.gateways;

import conferencesim.usecases.Messenger;
import java.io.*;

public class MessageGateway extends MasterGateway implements iGateway {

    /**
     * Takes an instance of Messenger and saves it as a .ser file in the serfiles directory
     * @param save the type of ManagerType to save it as
     * @param args the instance of Messenger to be saved
     */

    @Override
    public void saveFile(ManagerType save, Object[] args) {
        if (save == ManagerType.MESSENGER) {
            File file = new File("MessageInfo.txt");
            saveHelper(file, args[0]);
        }
    }

    /**
     * Loads a previously saved Messenger instance from the serfiles directory
     * If no saved instance exists, it creates a new instance of Messenger and returns that
     * @return an instance of Messenger
     */

    public Messenger loadFile(){
        try{
            FileInputStream fis = new FileInputStream("MessageInfo.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Messenger m = (Messenger)ois.readObject();
            ois.close();
            fis.close();
            return m;

        } catch (IOException | ClassNotFoundException io) {

            return new Messenger();
        }
    }


}
