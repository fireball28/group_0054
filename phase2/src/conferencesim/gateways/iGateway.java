package conferencesim.gateways;

public interface iGateway {

    void saveFile(ManagerType save, Object[] args);

    Object loadFile();

}
