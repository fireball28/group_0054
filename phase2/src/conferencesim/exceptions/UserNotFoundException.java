package conferencesim.exceptions;
import java.lang.Exception;

public class UserNotFoundException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {
        super();
    }
	
	public String getMessage() {
		return "No user was found for the given user ID.";
	}
}