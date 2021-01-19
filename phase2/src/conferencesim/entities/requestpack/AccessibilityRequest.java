package conferencesim.entities.requestpack;

import java.io.Serializable;

public class AccessibilityRequest extends Request implements Serializable {

    public AccessibilityRequest(String requestID, String UserID){
        super(requestID, UserID);
        type = "AccessibilityRequest";
    }
}
