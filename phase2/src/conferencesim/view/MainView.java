package conferencesim.view;

import java.util.List;

import conferencesim.controllers.CommandHelper;
import conferencesim.controllers.LoginController;
import conferencesim.exceptions.DoesNotMatchException;
import conferencesim.exceptions.InvalidConfirmationException;
import conferencesim.exceptions.UserNotFoundException;
import conferencesim.usecases.Displayable;

public class MainView implements Displayable {

    private CommandHelper ch_inst;
    private LoginController lc_inst;
    private String input;
    private boolean running;

    public MainView(LoginController lc) {
        this.lc_inst = lc;
    }

    public void setCh_inst(CommandHelper ch) {
        this.ch_inst = ch;
    }

    public MainView callView() {
        return this;
    }

    public void print(String s) {
        System.out.println(s);
    }

    public void printList(List<String> stuff) {
        if (stuff.isEmpty()) {
            System.out.println("No such objects were found...");
        } else {
            for (String s : stuff) {
                System.out.println(s);
            }
        }
    }

    public void injectSelfToUM(MainView mv) {
        lc_inst.injectionHelper(mv);
    }

    public void successful(){
        System.out.println("Successfully!");
    }

    public void setRunning(boolean b) {
        this.running = b;
    }

    public boolean getRunning() {
        return this.running;
    }

    private void promptUserID() {
        System.out.print("Login ID: ");
        this.input = lc_inst.takeUserID();
    }

    private void promptPassword() {
        System.out.print("Password: ");
        this.input = lc_inst.takePassword();
    }

    public void handleLogin() {
        boolean loginSuccessful = false;
        while (!loginSuccessful) {
            promptUserID();
            promptPassword();
            try {
                lc_inst.loginUser();
                loginSuccessful = lc_inst.isLoggedIn();
            } catch (UserNotFoundException unf) {
                break;
            } catch (DoesNotMatchException dnm) {
                System.out.println("Wrong Password. \nTry again");
                break;
            }
        }
        this.setRunning(true);
    }

    public void logout() {
        boolean confirm = false;
        System.out.println("Log out? [Y/n]");
        try {
            confirm = ch_inst.confirmationListener();
        } catch (InvalidConfirmationException ice) {
            this.logout();
        }
        if(confirm) {
            lc_inst.logoutUser();
        } else {
            System.out.println("Logout cancelled.");
        }
    }

    public void displayStartMenu() {
        System.out.println("\nConference Simulator 2000. Login or register?");
        System.out.println("Type q to quit.");
    }

    public String getInput() {
        return this.input;
    }

    public void displayMenu() {
        this.setRunning(true);
        System.out.print("> ");
        input = this.ch_inst.commandListener();
        if (input.equalsIgnoreCase("help")) {
            System.out.println(ch_inst.getHelp());
        } else if (input.toLowerCase().startsWith("help")) {
            System.out.println(ch_inst.getHelp(input.split(" ")[1]));
        } else if (input.equalsIgnoreCase("logout")) {
            logout();
        } else if (this.ch_inst.parseCommand(input)) {
        	if (!ch_inst.executeCommand()) {
        		System.out.println("The command was entered incorrectly.");
        		System.out.println("Usage: " + ch_inst.getHelp(input.split(" ")[0]));
        	}
        } else {
        	System.out.println("The command does not exist.");
        }
    }

}
