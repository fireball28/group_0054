package conferencesim.gateways;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public abstract class MasterGateway implements iGateway {

    public abstract void saveFile(ManagerType save, Object[] args);

    public abstract Object loadFile();

    /**
     * Takes an instance of file path and a Manager and saves it as a .ser file in the serfiles directory
     * @param file the path of the file to save the .ser file as
     * @param save the instance of Manager to be saved
     */
    public void saveHelper(File file, Object save) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(save);
            oos.close();
            fos.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}