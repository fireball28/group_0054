import conferencesim.controllers.CommandHelper;
import conferencesim.controllers.LoginController;
import conferencesim.controllers.RegisterController;
import conferencesim.controllers.cli.*;
import conferencesim.gateways.*;
import conferencesim.usecases.*;
import conferencesim.view.ErrorView;
import conferencesim.view.MainView;
import conferencesim.view.RegisterView;

import java.util.Scanner;

public class ConferenceMain {
	
	public static void main(String[] args) {
		MessageGateway mbd = new MessageGateway();
		EventGateway eg = new EventGateway();
		UserGateway ug = new UserGateway();
		RoomGateway rg = new RoomGateway();
		RequestGateway rqg = new RequestGateway();


		Scanner sc = new Scanner(System.in);

		UserManager um = ug.loadFile();
		EventManager em = eg.loadFile();
		RoomManager rm = rg.loadFile();
		Messenger m = mbd.loadFile();
		UserRequestsHelper urh = rqg.loadFile();
		rm.setRoomUpdater(em);

		EventStats es = new EventStats(em.getEventList());
		UserStats us = new UserStats(um.getUserList());

		ErrorView ev = new ErrorView();

		um.seteViewCaller(ev);
		LoginController logc = new LoginController(um);
		RegisterController regc = new RegisterController(um);

		MainView mv = new MainView(logc);
		RegisterView rv = new RegisterView(regc);
		mv.injectSelfToUM(mv);


		boolean mainRun = true;
		while (mainRun) {
			while (!logc.isLoggedIn()) {
				mv.displayStartMenu();
				String input = sc.nextLine();
				if (input.equalsIgnoreCase("login")) {
					mv.handleLogin();
				} else if (input.equalsIgnoreCase("register")) {
					rv.registerDisplay();
					break;
				} else if (input.equalsIgnoreCase("q") || input.equalsIgnoreCase("quit")) {
					System.out.println("quitting...");
					mainRun = false;
					mv.setRunning(false);
					break;
				} else {
					System.out.println("Invalid input, aborting!");
				}
			}

			if (logc.isLoggedIn()) {
				CommandHelper ch = constructCommandHelper(um, em, rm, m, urh, es, us);
				mv.setCh_inst(ch);
				ch.setViewBindings(mv, ev);
				System.out.println("Welcome! Type 'help' for a list of commands or information about a single command, type 'quit' to quit.");
			}
			while(mv.getRunning()) {
				mv.displayMenu();
				if (mv.getInput().equalsIgnoreCase("logout")) {
					if (!logc.isLoggedIn()) {
						System.out.println("Logging out...");
						mv.setRunning(false);
					} else {
						System.out.println("Type 'help' for a list of commands or information about a single command, type 'quit' to quit.");
					}
				} else if (mv.getInput().equalsIgnoreCase("quit") || mv.getInput().equalsIgnoreCase("q")) {
					System.out.println("Please log out first.");
				}
			}
		}

		eg.saveFile(ManagerType.EVENTMANAGER, new Object[]{em});
		mbd.saveFile(ManagerType.MESSENGER, new Object[]{m});
		rg.saveFile(ManagerType.ROOMMANAGER, new Object[]{rm});
		ug.saveFile(ManagerType.USERMANAGER, new Object[]{um});
		rqg.saveFile(ManagerType.REQUESTHELPER, new Object[]{urh});
		System.exit(0);
	}
	
	static CommandHelper constructCommandHelper(UserManager um, EventManager em, RoomManager rm, Messenger m,
												UserRequestsHelper urh, EventStats es, UserStats us) {
		EventController ec = new EventController(um, em, rm, es);
		MessageController mc = new MessageController(um, em, m);
		RoomController rc = new RoomController(rm);
		UserController uc = new UserController(um, us);
		RequestController rqc = new RequestController(urh);

		
		CommandHelper ch = new CommandHelper(um.getCurrUserPerms());
		ch.registerControllers(ec, mc, rc, uc, rqc);
		
		return ch;
	}
}
