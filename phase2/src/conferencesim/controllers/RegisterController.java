package conferencesim.controllers;

import conferencesim.exceptions.DoesNotMatchException;
import conferencesim.exceptions.DuplicateUserIDException;
import conferencesim.usecases.UserManager;

import java.util.Scanner;

public class RegisterController {
    String userID;
    String password;
    String passCheck;
    String role;
    UserManager um_inst;
    Scanner console;

    public RegisterController(UserManager um_inst) {
        this.userID = null;
        this.password = null;
        this.role = null;
        this.um_inst = um_inst;
        this.console = new Scanner(System.in);
        this.passCheck = null;
    }

    public void takeUserID() throws DuplicateUserIDException {
        userID = console.nextLine();
        if (um_inst.idExists(userID)) {
            throw new DuplicateUserIDException();
        }
    }

    public void takePassword() throws DoesNotMatchException {
        if (password == null) {
            password = console.nextLine();
            if (!(password.matches("^[a-zA-Z0-9]*$")) || password.equals("")) {
                throw new DoesNotMatchException();
            }
        } else {
            passCheck = console.nextLine();
            if (!password.equals(passCheck)) {
                throw new DoesNotMatchException();
            }
        }
    }

    public boolean takeRole() {
        role = console.nextLine();
        return role.matches("(?i)^(speaker|organizer|attendee)");
    }

    public boolean isLoggedIn() {
        return um_inst.getCurrUserLoggedIn();
    }
    
    public void registerUser() {
        um_inst.registerUser(userID, password, role);
        password = null;
        passCheck = null;
    }

    public void setRoleAttendee() {
        role = "attendee";
    }

}
