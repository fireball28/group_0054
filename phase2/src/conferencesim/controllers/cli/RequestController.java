package conferencesim.controllers.cli;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import conferencesim.usecases.UserRequestsHelper;

public class RequestController extends CommandController{

    public RequestController(UserRequestsHelper urh){
        this.urh_inst = urh;
    }

    public boolean addRequests(String requestID, String userID, String type){
        return urh_inst.addRequest(requestID, userID, type);
    }

    public boolean changeStatus(String requestID, String status){
        return urh_inst.updateRequest(requestID, status);
    }

    public void viewRequests(){
        urh_inst.viewRequests();
    }

    public void viewRequestsByUser(String userID){
        urh_inst.getRequestByUser(userID);
    }

    @Override
    public Method getApplicableMethod(String command, int argLen) throws NoSuchElementException {
        Method[] m_arr = this.getClass().getMethods();
        Optional<Method> om = Arrays.asList(m_arr).stream().filter(m -> m.getName().equalsIgnoreCase(command) && m.getParameterCount() == argLen).findFirst();
        return om.orElse(null);
    }

    @Override
    public void execute(String command, List<Object> callArgs) {
        try {
            getApplicableMethod(command, callArgs.size()).invoke(this, callArgs.toArray());
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException exc) {
            exc.printStackTrace();
        }
    }
}
