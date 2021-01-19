package conferencesim.controllers.cli;

import java.lang.reflect.Method;
import java.util.List;
import java.util.NoSuchElementException;

import conferencesim.usecases.Displayable;
import conferencesim.usecases.EDisplayable;

public interface Commandable {
	
	/**
	 * Gets the method responsible for executing the given command
	 * @param command name of command to lookup
	 * @param argLen number of arguments provided
	 * @return Method object for command
	 * @throws NoSuchElementException
	 */
	Method getApplicableMethod(String command, int argLen) throws NoSuchElementException;
	
	/**
	 * Executes the given command with the given arguments
	 * @param command name of command to be executed
	 * @param callArgs list of arguments provided by user
	 */
	void execute(String command, List<Object> callArgs);
	
	/**
	 * Sets the applicable views for this Commandable object
	 * @param d main displayable
	 * @param e error displayable
	 */
	void setCurrViews(Displayable d, EDisplayable e);
}
