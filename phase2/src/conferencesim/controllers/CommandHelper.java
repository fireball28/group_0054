package conferencesim.controllers;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Collectors;

import conferencesim.controllers.cli.CommandController;
import conferencesim.controllers.cli.Commandable;
import conferencesim.entities.eventpack.Room;
import conferencesim.exceptions.InvalidConfirmationException;
import conferencesim.usecases.CommandManager;
import conferencesim.usecases.Displayable;
import conferencesim.usecases.EDisplayable;

public class CommandHelper {
	
	protected CommandManager cm_inst;
	
	protected Scanner sc = new Scanner(System.in);
	
	private final DateTimeFormatter dfFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	private List<Commandable> cList;
	
	private String currCommand = "";
	private List<String> rawArgsList;
	
	// Map for converting string input to applicable method parameter type
	private final Map<Class<?>, Function<String, Object>> argMap = new HashMap<Class<?>, Function<String, Object>>() {
		private static final long serialVersionUID = 1L; {
			
		put(int.class, s -> Integer.parseInt(s));
		put(String.class, s -> s);
		put(LocalDateTime.class, s -> LocalDateTime.parse(s, dfFormatter));
		put(List.class, s -> Arrays.asList(s.split(",")));
	}};
	
	public CommandHelper(List<String> permList) {
		this.cList = new ArrayList<>();
		this.cm_inst = new CommandManager(permList);
		
		this.rawArgsList = new ArrayList<>();
	}
	
	/**
     * Open a scanner object and get input for next command for use in model view
     * @return User input
     */
    public String commandListener() {
    	return sc.nextLine();
	}
    
    /**
     * Open a scanner object and get input for confirmation (Y/N)
     * @return boolean indicating positive or negative response
     * @throws InvalidConfirmationException
     */
	public boolean confirmationListener() throws InvalidConfirmationException {
    	String input = sc.nextLine();
    	if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("yes")) {
    		return true;
		} else if (input.equalsIgnoreCase("N") || input.equalsIgnoreCase("no")) {
    		return false;
		} else {
    		throw new InvalidConfirmationException();
		}
	}
	
	/**
	 * Adds instances of Commandable to private list
	 * @param c Commandables to be added
	 */
	public void registerControllers(Commandable... c) {
		cList.addAll(Arrays.asList(c));
	}
	
	/**
	 * Sets the view bindings for all Commandable objects registred under this CommandHelper object
	 * @param d Displayable to register
	 * @param ed EDisplayable to register
	 */
	public void setViewBindings(Displayable d, EDisplayable ed) {
		for (Commandable c : cList) {
			c.setCurrViews(d, ed);
		}
	}
	
	/**
     * Parses a set of instructions given in string form, in other words, a command matching
     * the following: <command name> <arg0> <arg*>, where each argument is separated by
     * spaces and arguments containing more than one word are surrounded by quotation marks.
     * @param input Input to process
     * @return true if command exists, false otherwise
     */
	public boolean parseCommand(String input) {
		this.currCommand = "";
		this.rawArgsList = new ArrayList<>();
		List<String> commandList = cm_inst.getCommandList();
		String lookupRegex = "(?i)^(" + String.join("|", commandList) + ").*";
		if (input.matches(lookupRegex)) {
			StringTokenizer st = new StringTokenizer(input, " ");
			currCommand = st.nextToken();
			String currToken;
			while (st.hasMoreTokens()) {
				StringBuilder quotedArg = new StringBuilder();
				currToken = st.nextToken();
				if (currToken.startsWith("\"")) {
					if (currToken.endsWith("\"")) {
						rawArgsList.add(currToken.substring(1, currToken.length() - 1));
						continue;
					}
					quotedArg.append(currToken.substring(1, currToken.length())).append(" ");
					String nextToken = st.nextToken();
					while (!nextToken.endsWith("\"")) {
						quotedArg.append(nextToken).append(" ");
						nextToken = st.nextToken();
					}
					quotedArg.append(nextToken.substring(0, nextToken.length() - 1));
				}
				rawArgsList.add((quotedArg.length() != 0) ? quotedArg.toString() : currToken);
			}
			return true;
		}
		return false;
	}


	/**
     * Gets method from subject controller and executes it
     */
	public boolean executeCommand() {
		for (Iterator<Commandable> it = cList.iterator(); it.hasNext(); ) {
			Commandable c = it.next();
			Method m = c.getApplicableMethod(currCommand, rawArgsList.size());
			if (m != null) {
				Class<?>[] ptypes = m.getParameterTypes();
				List<Object> callArgs = new ArrayList<>();
				for (int i = 0; i < ptypes.length; i++) {
					if (rawArgsList.size() <= i) {
						callArgs.add(Optional.empty());
					} else {
						try {
							callArgs.add(argMap.get(ptypes[i]).apply(rawArgsList.get(i)));
						} catch (Exception e) {
							return false;
						}
					}
				}
				c.execute(currCommand, callArgs);
				return true;
			}
		}
		return false;
	}
	
	/**
     * Get the documentation for all commands the user has access to
     * @return String containing complete documentation for user
     */
    public String getHelp() {
    	String doc = cm_inst.getCompleteDocumentation();
    	if (doc != null && doc.contains("*")) {
    		doc = doc + "\n[Notice] An asterisk (*), indicates an optional argument.";
    	}
    	return doc;
    }
    
    /**
     * Get the documentation for a specific command
     * @param command Name of command to retrieve doc from
     * @return String containing documentation for specific command
     */
    public String getHelp(String command) {
    	String doc = cm_inst.getFilteredDocumentation(command);
    	if (doc != null && doc.contains("*")) {
    		doc = doc + "\n[Notice] An asterisk (*), indicates an optional argument.";
    	}
    	return doc;
    }
}
