package conferencesim.exceptions;
import java.lang.Exception;

public class DoesNotMatchException extends Exception{
	
	private static final long serialVersionUID = 1L;

	public DoesNotMatchException() {
        super();
    }
    
	@Override
    public String getMessage() {
		return "The password entered does not match our records.";
    }

}
