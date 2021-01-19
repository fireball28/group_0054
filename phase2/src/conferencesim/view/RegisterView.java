package conferencesim.view;

import conferencesim.controllers.RegisterController;
import conferencesim.exceptions.*;


public class RegisterView {
    private RegisterController rc_inst;

    public RegisterView(RegisterController rc) {
        this.rc_inst = rc;
    }

    public void registerDisplay() {
        System.out.println("Welcome to registration page.");
        if (!rc_inst.isLoggedIn()) {
            rc_inst.setRoleAttendee();
        } else {
            System.out.println("Choose a role for this user: (Organizer, Speaker, Attendee)");
            while (!rc_inst.takeRole()) {
                System.out.println("The role you entered does not match one of the following: Organizer, Speaker, Attendee.");
            }
        }
        promptUserID();
        promptNewPassword();
        promptPasswordAgain();
        handleRegister();
    }

    public void handleRegister() {
        rc_inst.registerUser();
        //System.out.println("Registration complete.");
    }


    public void promptUserID() {
        System.out.println("\nPlease choose a userID: ");
        try {
            rc_inst.takeUserID();
            System.out.println("\nUserID valid.");
        } catch (DuplicateUserIDException dui) {
            System.out.println("\nSomebody is already using this UserID.");
            promptUserID();
        }
    }

    public void promptNewPassword() {
        System.out.println("\nPlease enter a password: ");
        try {
            rc_inst.takePassword();
            System.out.println("Password valid.");
        } catch (DoesNotMatchException dnm) {
            System.out.println("\nPassword invalid, please try again.");
            promptNewPassword();
        }
    }

    public void promptPasswordAgain() {
        System.out.println("Please reenter your password: ");
        try {
            rc_inst.takePassword();
            System.out.println("Password confirmed.");
        } catch (DoesNotMatchException dnm) {
            System.out.println("\nPassword does not match, please try again.");
            promptPasswordAgain();
        }
    }


}
