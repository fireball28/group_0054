package conferencesim.controllers;

import conferencesim.usecases.UserManager;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LoginController implements Commandable {

    UserManager um_inst;

    public LoginController(UserManager um) {
        this.um_inst = um;
    }

    @Override
    public List<String> getCommandList() {
        return Arrays.asList("changepassword", "logout");
    }

    @Override
    public boolean run(Scanner sc, String command) {
        if (command.equalsIgnoreCase("changepassword")) {
            return changePassword(sc);
        } else if (command.equalsIgnoreCase("logout")) {
            return logout(sc);
        }
        return false;
    }

    /**
     * Sends a login request to a given UserManager object
     * @param um UserManager to login to
     * @param sc Scanner for user input
     * @return boolean indicating login success
     */
    public static boolean requestLogin(UserManager um, Scanner sc) {
        String userID, password, role;
        System.out.println("\nLogin");
        System.out.println("Please specify your role: Attendee/Organizer/Speaker");
        role = sc.nextLine();
        if (!role.equalsIgnoreCase("attendee") && !role.equalsIgnoreCase("organizer")
        && !role.equalsIgnoreCase("speaker")) {
            System.out.println("\nInput not accepted, please try again.");
            return false;
        }
        System.out.println("UserID: ");
        userID = sc.nextLine();
        while (!um.idExists(userID)) {
            System.out.println("\n[Error] UserID does not exist, please try again.");
            System.out.println("UserID: ");
            userID = sc.nextLine();
        }
        System.out.println("Password: ");
        password = sc.nextLine();
        while (!um.loginUser(userID, password)) {
            System.out.println("\nPassword incorrect, please try again.");
            System.out.println("Password: ");
            password = sc.nextLine();
        }
        um.getCurrUser().setRole(role);
        um.loginUser(userID, password);
        return true;
    }

    /**
     * Sends a register request to a given UserManager object
     * @param um UserManager to register new account on
     * @param sc Scanner for user input
     * @return boolean indicating successful registration
     */
    public static boolean requestRegister(UserManager um, Scanner sc) {
        String userID, password = null;
        boolean confirmed_pass = false;
        System.out.println("\nRegister");
        System.out.println("Please choose a userID: ");
        userID = sc.nextLine();
        boolean validPass = false;
        while (!validPass) {
            System.out.println("Please set a password: ");
            password = sc.nextLine();
            if (!(password.matches("^[a-zA-Z0-9]*$")) || password.equals("")) {
                System.out.println("\nPassword can only contain alphanumeric characters and no spaces.");
            } else {
                validPass = true;
            }
        }
        System.out.println("Password valid. Please confirm the password: ");
        while (!confirmed_pass) {
            if (sc.nextLine().equals(password)) {
                confirmed_pass = true;
            } else {
                System.out.println("The password doesn't match, please reenter.");
            }
        }
        return um.registerUser(userID, password);
    }

    /**
     * Logs current user out of system
     * @param sc Scanner for user input
     * @return boolean indicating successful logout
     */
    public boolean logout(Scanner sc) {
        String confirmation;
        System.out.println("Logout");
        System.out.println("Are you sure you would like to log out of your account? (Yes / No)");
        confirmation = sc.nextLine();
        if ("No".equalsIgnoreCase(confirmation)) {
            return false;
        } else if ("Yes".equalsIgnoreCase(confirmation)) {
            um_inst.logoutUser(um_inst.getCurrUser());
            System.out.println("Successfully logged out.");
            return true;
        }
        System.out.println("Please enter a valid input. (Yes / No)");
        return false;
    }

    /**
     * Changes current user password
     * @param sc Scanner for user input
     * @return boolean indicating successful password change
     */
    public boolean changePassword(Scanner sc) {
        String oldPassword;
        String userID;
        System.out.println("Enter your unique userID: ");
        userID = sc.nextLine();
        while (!um_inst.idExists(userID)) {
            System.out.println("UserID not found. Please try again.");
            System.out.println("Enter your unique userID: ");
            userID = sc.nextLine();
        }
        System.out.println("Enter current password: ");
        oldPassword = sc.nextLine();
        if (um_inst.getCurrUser().getPassword().equals(oldPassword)) {
            return confirmHelper(sc);
        } else {
            System.out.println("Wrong password");
            changePassword(sc);
        }
        return false;
    }

    private boolean confirmHelper(Scanner sc) {
        String newPassword = null, confirmPassword;
        boolean validPass = false;
        boolean confirmed_pass = false;
        while (!validPass) {
            System.out.println("Please set a password: ");
            newPassword = sc.nextLine();
            if (!(newPassword.matches("^[a-zA-Z0-9]*$")) || newPassword.equals("")) {
                System.out.println("Password can only contain alphanumeric characters and no spaces.");
            } else {
                validPass = true;
            }
        }
        System.out.println("Password valid. Please confirm the password: ");
        while (!confirmed_pass) {
            if (sc.nextLine().equals(newPassword)) {
                confirmed_pass = true;
            } else {
                System.out.println("The password doesn't match, please reenter.");
            }
        }
        um_inst.getCurrUser().setPassword(newPassword);
        System.out.println("Password changed!");
        return true;
    }
}
