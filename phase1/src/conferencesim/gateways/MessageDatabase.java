package conferencesim.gateways;

import conferencesim.entities.Message;
import conferencesim.usecases.Messenger;

import java.io.*;

public class MessageDatabase {

    public void saveMessenger(Messenger toSave){

        try {
            File file = new File("messenger.ser");
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(toSave);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in messenger.ser\n");

        }

        catch (IOException i) {
            i.printStackTrace();
        }
    }

    public Messenger loadMessenger() throws ClassNotFoundException {

        try {
            FileInputStream fis = new FileInputStream("messenger.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Messenger messenger = (Messenger) ois.readObject();
            ois.close();

            System.out.printf("Serialized Message data is loaded successfully\n");

            return messenger;
        }

        catch(IOException i){
            return new Messenger();
        }

    }
}
