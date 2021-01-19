package conferencesim.entities.requestpack;

import java.io.Serializable;

public class DietaryRequest extends Request implements Serializable {

    public DietaryRequest(String requestID, String UserID){
        super(requestID, UserID);
        type = "DietaryRequest";
    }

}
