package conferencesim.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import conferencesim.view.MainView;

import conferencesim.exceptions.DoesNotMatchException;
import conferencesim.exceptions.UserNotFoundException;
import conferencesim.usecases.UserManager;

public class LoginController {

    String userID;
    String password;
    UserManager um_inst;
    Scanner sc;
    
    public LoginController(UserManager um_inst) {
        this.userID = "";
        this.password = "";
        this.um_inst  = um_inst;
        this.sc = new Scanner(System.in);
    }

    public String takeUserID() {
        return this.userID = sc.nextLine();
    }

    public String takePassword() {
        return this.password = sc.nextLine();
    }

    public void loginUser() throws UserNotFoundException, DoesNotMatchException {
    	um_inst.loginUser(this.userID, this.password);
    }
    
    public void logoutUser() {
    	um_inst.logoutUser(um_inst.getCurrUser());
    }
    
    public Map<String, String> getCredentials() {
		if (um_inst.getCurrUser() == null) {
			return null;
		}
		
		Map<String, String> cMap = new HashMap<>();
		cMap.put("userID", um_inst.getCurrUser().getUserID());
		cMap.put("password", um_inst.getCurrUser().getPassword());
		return cMap;
    }
    
    public boolean isLoggedIn() {
    	return um_inst.getCurrUserLoggedIn();
    }

    public void injectionHelper(MainView mv) {
        this.um_inst.setViewCaller(mv);
    }
}
