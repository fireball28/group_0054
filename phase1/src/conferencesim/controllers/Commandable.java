package conferencesim.controllers;

import java.util.List;
import java.util.Scanner;

public interface Commandable {

    /**
     * Retrieves a list of commands possible for this object
     * @return List of registered commands
     */
    List<String> getCommandList();

    /**
     * Runs a given command
     * @param sc Scanner to use for user input
     * @param command command to run
     * @return boolean indicating successful command execution
     */
    boolean run(Scanner sc, String command);
}
