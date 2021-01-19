package conferencesim.entities.requestpack;

import java.io.Serializable;

public class Request implements Serializable {
    /**
     * Phase 2 extension - a request submitted by user
     */

    protected String status;
    protected String type;
    protected String UserID;
    protected String requestID;

    public Request(String requestID, String UserID){
        this.status = "pending";
        this.UserID = UserID;
        this.requestID = requestID;
    }

    public String getRequestID(){return requestID;};

    public String getType(){
        return type;
    }

    public String getUser(){
        return UserID;
    }

    public String getStatus(){return status; }

    public boolean changeStatusAsAddressed(){
        status = "Addressed";
        return true;
    }

    public boolean changeStatusAsPending(){
        status = "Pending";
        return true;
    }

    @Override
    public String toString(){
        return "Request ID:" + this.getRequestID() + "User ID: " + this.getUser() + ", request type:" +
                this.getType() + ", request status:" + this.getStatus();
    }
}
