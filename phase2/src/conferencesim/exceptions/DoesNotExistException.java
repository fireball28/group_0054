package conferencesim.exceptions;
import java.lang.Exception;

public class DoesNotExistException extends Exception {
    public DoesNotExistException() {
        super();
    }

    @Override
    public String getMessage() {
        return "What you are looking for does not exist.";
    }
}
