package conferencesim.usecases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import conferencesim.entities.requestpack.AccessibilityRequest;
import conferencesim.entities.requestpack.DietaryRequest;
import conferencesim.entities.requestpack.Request;

public class UserRequestsHelper extends Usecase implements Serializable {
    private List<Request> requests;

    public UserRequestsHelper() {
        this.requests = new ArrayList<Request>();
    }

    /**
     * adds a request
     */

    public boolean addRequest(String requestID, String UserID, String type) {
        if (type.equals("DietaryRequest")) {
            for (Request r : requests) {
                if (r.getType().equals(type) && r.getUser().equals(UserID)) {
                    view.print("User already has this request");
                    return false;
                }
            }
            Request rq = new DietaryRequest(requestID, UserID);
            this.requests.add(rq);
            view.print("Successful!");
            return true;

        } else if (type.equals("AccessibilityRequest")) {
            for (Request r : requests) {
                if (r.getType().equals(type) && r.getUser().equals(UserID)) {
                    view.print("User already has this request");
                    return false;
                }
            }
            Request rq = new AccessibilityRequest(requestID, UserID);
            this.requests.add(rq);
            view.print("Successful!");
            return true;
        }
        view.print("Action unsuccessful");
        return false;
    }

    public boolean updateRequest(String requestID, String status){
        Request request = getRequestByID(requestID);
        if(request==null){
            view.print("No requestID so request was not updated");
            return false;
        }
        if(status.equalsIgnoreCase("Addressed")) {
            view.print("Request updated to addressed");
            return request.changeStatusAsAddressed();
        }else if(status.equalsIgnoreCase("Pending")){
            view.print("Request updated to pending");
            return request.changeStatusAsPending();
        }else{
            view.print("Request was not updated");
            return false;
        }
    }

    public void viewRequests(){
        List<String> requestString = new ArrayList<String>();
        for (Request r:requests){
            requestString.add(r.toString());
        }
        view.printList(requestString);
    }

    public Request getRequestByID(String requestID){
        for(Request r: requests){
            if(r.getRequestID().equals(requestID)) {
                view.print(r.toString());
                return r;
            }
        }

        eView.callView().requestDoesNotExist();
        return null;
    }

    public void getRequestByUser(String UserID){
        List<String> userRequest = new ArrayList<String>();
        for(Request r: requests) {
            if (r.getUser().equals(UserID)) {
                userRequest.add(r.toString());
            }
        }
        view.printList(userRequest);
    }

}
