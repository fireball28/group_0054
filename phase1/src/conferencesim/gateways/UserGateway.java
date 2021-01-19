package conferencesim.gateways;

import conferencesim.usecases.Messenger;
import conferencesim.usecases.UserManager;

import java.io.*;

public class UserGateway {

    public void saveUserRegistration(UserManager UserInfo) {

        try {
            File file = new File("userInfo.txt");
            FileOutputStream infoBank = new FileOutputStream(file);
            //Would this be overwriting the old file every single time
            //If so should we make use an if statement?
            ObjectOutputStream info = new ObjectOutputStream(infoBank);
            info.writeObject(UserInfo);
            info.close();
            infoBank.close();
            System.out.printf("User registration has been saved\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UserManager readUserInfo() throws ClassNotFoundException {
        try {
            FileInputStream fis = new FileInputStream("userInfo.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            UserManager um = (UserManager) ois.readObject();
            ois.close();
            System.out.printf("Serialized User data is loaded successfully\n");

            return um;
        }

        catch(IOException i){
            return new UserManager();
        }

    }
}