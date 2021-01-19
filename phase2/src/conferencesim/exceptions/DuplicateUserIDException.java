package conferencesim.exceptions;
import java.lang.Exception;

public class DuplicateUserIDException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DuplicateUserIDException() {
        super();
    }
    
	@Override
    public String getMessage() {
		return "A user with that ID already exists.";
    }
}

