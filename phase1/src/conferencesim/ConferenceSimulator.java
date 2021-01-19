package conferencesim;

import conferencesim.gateways.EventGateway;
//import conferencesim.gateways.UserGateway;
import conferencesim.controllers.*;
import conferencesim.gateways.UserGateway;
import conferencesim.usecases.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/*
   Created by Seoyoon on 11/8/2020
 */
public class ConferenceSimulator {

    public static void main(String[] args) throws ClassNotFoundException {
        boolean systemRunning = true;
        MessageDatabase mbd = new MessageDatabase();
        EventGateway eg = new EventGateway();
        UserGateway ug = new UserGateway();
        Scanner console = new Scanner(System.in);
        UserManager um = ug.readUserInfo();
        EventManager em = eg.loadEvents();
        Messenger m = mbd.loadMessenger(); // if there is no fle to load a new instance of Messenger is returned
        System.out.println("\n Welcome to Conference Simulator 2000");

        boolean authenticated = false;
        while (systemRunning) {
            System.out.println("\nChoose an option (Login, Register, Quit): ");
            String choice = console.nextLine();
            if ("Login".equalsIgnoreCase(choice)) {
                authenticated = LoginController.requestLogin(um, console);
            } else if ("Register".equalsIgnoreCase(choice)) {
                LoginController.requestRegister(um, console);
            } else if ("Quit".equalsIgnoreCase(choice)) {
                eg.saveEvents(em);
                mbd.saveMessenger(m);
                systemRunning = false;
            } else {
                System.out.println("\nPlease choose one of the options above.");
            }

            if (!authenticated) continue;

            Map<String, Commandable> commandControllers = getCMap(um, em, m);
            List<String> cmdList = new ArrayList<>();
            commandControllers.values().stream().map(Commandable::getCommandList).collect(Collectors.toList())
                    .forEach(cmdList::addAll);

            System.out.println("Welcome " + um.getCurrUserID());
            System.out.println("Type 'help' for a list of commands, 'logout' to exit.");

            while (authenticated) {
                System.out.print("> ");
                String input = console.nextLine();
                if (input.equalsIgnoreCase("logout")) {
                    if (commandControllers.get("login").run(console, "logout")) {
                        mbd.saveMessenger(m);
                        eg.saveEvents(em);
                        ug.saveUserRegistration(um);
                        authenticated = false;
                    }
                } else if (input.equalsIgnoreCase("h") || input.equalsIgnoreCase("help")) {
                    System.out.println("Possible commands: ");
                    System.out.println(String.join(", ", cmdList));
                } else {
                    Predicate<Commandable> lookupPred = c -> c.getCommandList().stream().anyMatch(s -> s.equalsIgnoreCase(input));
                    Optional<Commandable> c = commandControllers.values().stream().filter(lookupPred).findFirst();
                    if (c.isPresent()) {
                        c.get().run(console, input);
                    } else {
                        System.out.println("The command you entered is not defined or you do not have the requisite permissions.");
                    }
                }
            }
        }
    }

    /**
     * Returns a map of Commandables organized by role/functionality
     * @param um UserManager to load
     * @param em EventManager to load
     * @param m Messenger to load
     * @return Map containing Commandables accessible by their functionality (possibilities: user, login, admin, speaker)
     */
    public static Map<String, Commandable> getCMap(UserManager um, EventManager em, Messenger m) {
        Map<String, Commandable> cMap = new HashMap<>();
        cMap.put("user", new UserController(um, em, m));
        cMap.put("login", new LoginController(um));
        if (um.getCurrUserRole().equalsIgnoreCase("organizer")) {
            cMap.put("admin", new AdminController(um, em, m));
        } else if (um.getCurrUserRole().equalsIgnoreCase("speaker")) {
            cMap.put("speaker", new SpeakerController(um, em, m));
        }
        return cMap;
    }
}
