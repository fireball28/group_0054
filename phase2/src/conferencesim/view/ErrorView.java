package conferencesim.view;

import conferencesim.usecases.EDisplayable;

public class ErrorView implements EDisplayable {


    //error message for Request Controller
    public void notOrganizer(){
        System.out.println("A non-organizer cannot add any requests");
    }

    //error message for EventManager - AddEvent
    public void addEventsInvalidParameter(){
        System.out.println("The parameters your entered are not valid. Only enter one of the: SpeakerID or " +
                "list of speakers");

    }


    public void unsuccessful(){System.out.println("The action was unsuccessful");}
    //error for UserManager
    public void UserNotFound(){
        System.out.println("The userID you entered does not exist");
    }

    //error for EventManager
    public void roomDoesNotExist(){
        System.out.println("The room you entered does not exist");
    }

    //error for EventManager
    public void eventExists(){
        System.out.println("The event already exists");
    }

    //error for EventManager
    public void eventDoesNotExist(){
        System.out.println("The event does not exists");
    }

    //error for EventManager
    public void eventNotFound(){
        System.out.println("The event was not found");
    }

    //error for EventManager
    public void eventCoincides(){
        System.out.println("The event is already happening");
    }

    //error for EventManager
    public void speakerNotAvailable(){
        System.out.println("The speaker is not available");
    }

    //error for RoomManager
    public void roomAlreadyAdded(){
        System.out.println("The room name already exists");
    }

    //error for Messenger
    public void messageDoesNotExist(){
        System.out.println("The message does not exists");
    }

    //error for Messenger
    public void noMessagesInbox(){
        System.out.println("No messages found in the inbox");
    }

    //error for Messenger
    public void noMessagesTrash(){
        System.out.println("No messages found in the trash");
    }

    //error for Messenger
    public void noMessagesMessageList(){
        System.out.println("No messages found in Message List");
    }

    //error for UserRequestHelper
    public void requestDoesNotExist(){
        System.out.println("The request with that requestID does not exist");
    }

    @Override
    public ErrorView callView() {
        return this;
    }
}
