package conferencesim.controllers.cli;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import conferencesim.usecases.RoomManager;

public class RoomController extends CommandController {
	
	public RoomController(RoomManager rm) {
		this.rm_inst = rm;
	}
	
	public void addRoom(String roomName, int capacity, String type) {
    	rm_inst.addRoom(roomName, capacity, type);
    }
    
    public void removeRoom(String roomName) {
    	rm_inst.removeRoom(roomName);
    }
	
	public void viewAllRooms() {
    	rm_inst.viewAllRooms();
	}

	public void viewRoomTechnologies(String roomName) {
		rm_inst.viewRoomTechnologies(roomName);
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
