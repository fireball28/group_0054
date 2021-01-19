package conferencesim.controllers.cli;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import conferencesim.usecases.*;

public class UserController extends CommandController {

	public UserController(UserManager um, UserStats us) {

		this.um_inst = um;
		this.us_inst = us;
	}
	
	public void addFriend(String userID) {
		um_inst.addFriend(um_inst.getCurrUserID(), userID);
		um_inst.addFriend(userID, um_inst.getCurrUserID());
	}
	
	public void viewFriends() {
		um_inst.viewFriends();
	}
	
	public void removeFriend(String userID) {
		um_inst.deleteUserFriend(um_inst.getCurrUserID(), userID);
		um_inst.deleteUserFriend(userID, um_inst.getCurrUserID());
	}
	
	public void createSpeakerAccount(String userID, String password) {
		um_inst.registerUser(userID, password, "Speaker");
	}
	
	public void createAccount(String userID, String password, String role) {
		um_inst.registerUser(userID, password, role);
	}

	public void usersRegistered() {
		us_inst.usersRegistered();
	}

	public void speakersRegistered() {
		us_inst.speakersRegistered();
	}

	public void numFriends() {
		us_inst.numFriends(um_inst.getCurrUser());
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
