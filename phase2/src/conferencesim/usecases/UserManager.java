package conferencesim.usecases;

import conferencesim.entities.eventpack.Event;
import conferencesim.entities.eventpack.Room;
import conferencesim.entities.userpack.*;
import conferencesim.exceptions.DoesNotMatchException;
import conferencesim.exceptions.UserNotFoundException;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserManager extends Usecase implements Serializable {

    private List<User> userList;
    private User currUser = null;

    /**
     * Creates an empty UserManager.
     */
    public UserManager() {
        userList = new ArrayList<>();
        User admin = new Admin("admin", "8888");
        userList.add(admin);
    }

    /**
     * Get the User entity of the current user in this UserManager
     * @return User object if it is set, null otherwise
     */
    public User getCurrUser() {
        return currUser;
    }

    /**
     * Get a User entity with the given ID
     * @param userID ID of user to retrieve
     * @return User object if userID is registered, null otherwise
     */
    public User getUserWithID(String userID) {
        for (User u : userList) {
            if (userID.equals(u.getUserID())) return u;
        }
        eView.callView().UserNotFound();
        return null;
    }

    /**
     * Get a list of all users registered
     * @return List object containing user entities
     */
    public List<User> getUserList() {
        return this.userList;
    }

    /**
     * Get a list of user IDs for all users registered in this UserManager
     * @return List object containing user IDs currently registered
     */
    public List<String> getUserIDList() {
        List<String> userIDs = new ArrayList<>();
        for (User u: this.getUserList()) {
            userIDs.add(u.getUserID());
        }
        view.printList(userIDs);
        return userIDs;
    }

    /***
     * Get a list of speaker IDs for all speakers registered in this UserManager
     * @return List object containing speaker IDs currently registered
     */
    public List<String> getAllSpeakerIDs() {
        List<String> speakerIDs = new ArrayList<>();
        for (User u: this.getUserList()) {
            if (u.getRole().equals("Speaker")) {
                speakerIDs.add(u.getUserID());
            }
        }
        view.printList(speakerIDs);
        return speakerIDs;
    }

    /**
     * Return true if the userID exists in the user list else, return false.
     * @param userID id of the user that is checked.
     * @return true if the userID exists in the user list else, return false.
     */
    public boolean idExists(String userID) {
        for (User u : userList) {
            if (userID.equals(u.getUserID())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add a user to this user manager. Return false if there is an existing user in the user list with the same user id
     * as userID else, add the user to the user list and return true.
     * @param userID id of the user to be added to the UM.
     * @param password password of the user to be added to the UM.
     */


    //Overloaded method with role input
    public void registerUser(String userID, String password, String role) {
        if (idExists(userID)) {
            view.print("Register not successful, ID exists.");
            return;
        }

        if (role.equalsIgnoreCase("attendee")) {
            User user = new Attendee(userID, password);
            this.userList.add(user);
            view.callView().print("Registration complete");
        } else if (role.equalsIgnoreCase("speaker")) {
            User user = new Speaker(userID, password);
            this.userList.add(user);
            view.callView().print("Registration complete");
        } else if (role.equalsIgnoreCase("organizer")) {
            User user = new Organizer(userID, password);
            this.userList.add(user);
            view.callView().print("Registration complete");
        } else {
            view.print("not a valid role...");
        }
    }


    /**
     * Remove a user from this user manager. Return true if the user list contains userID hence, logs out the user and
     * remove the user from the user list else, return false.
     * @param userID id of the user to be removed from the UM.
     * @return true if the user list contains userID hence, logs out the user and remove the user from the user list
     * else, return false.
     */
    public boolean deleteUser(String userID) {
        User u = this.getUserWithID(userID);
        if (this.userList.contains(u)) {
            this.logoutUser(u);
            this.userList.remove(u);
            view.print("User successfully deleted");
            return true;
        } else {
            view.print("[Error] No such user exists");
            return false;
        }
    }

    /**
     * Allows the user to log in. Return true if the user enters the correct password and is not already logged it. Then
     * set the current user to be the user and log in status of the user to be true.
     * @param userID id of the user that is used to log in.
     * @param password of the user that is used to log in.
     * @return true if the user enters the correct password and is not already logged it. Then set the current user to
     * be the user and log in status of the user to be true.
     */
    public boolean loginUser(String userID, String password) throws UserNotFoundException, DoesNotMatchException {
        User u = getUserWithID(userID);
        if (u == null) {
            throw new UserNotFoundException();
        }
        if (!(password.equals(u.getPassword()))) {
            throw new DoesNotMatchException();
        }
        if (!u.getLoggedIn()) {
            currUser = u;
            u.setLoggedIn(true);
            view.callView().print("Successful!");
            return true;
        }
        view.callView().print("User did not successfully log in");
        return false;
    }

    /**
     * Allows the user to log out. Return true if the user is logged in, then set current user to null and
     * log in status of the user to be false.
     * @param u user that is logged out from the system.
     * @return true if the user is logged in, then set current user to null and log in status of the user to be false.
     */
    public void logoutUser(User u) {
        if (u != null && u.getLoggedIn()) {
            currUser = null;
            u.setLoggedIn(false);
        }
    }

    public void viewFriends() {
        view.printList(this.getCurrUser().getFriends());
    }

    /**
     * Add another user to the user's friend list. Return true if the user that is being added exists and does not
     * already exist in the user's friends list.
     * @param userID id of the user.
     * @param userToAdd user's friend that is going to be added.
     * @return true if the user that the user is trying to add exists and does not already exist in the user's friends
     * list.
     */

    public void addFriend(String userID, String userToAdd) {
        User u = getUserWithID(userID);
        if (u == null) {
            view.print("This will probably never get called");
        } else if (!u.getFriends().contains(userToAdd)) {
            u.addFriends(userToAdd);
            if (this.getCurrUserID() == userID) {
            	view.print("Successfully added " + userToAdd + " as a friend");
            }
        } else {
            view.print("[Warning] Username already in messaging list, skipping...");
        }
    }

    /**
     * Remove another user from user's friend list. Return true if the user in question exists and is present in this
     * user's friends list.
     * @param userID id of the user.
     * @param userToDel user's friend to be removed.
     * @return true if user to delete exists and is present in the user's friend list.
     */
    public void deleteUserFriend(String userID, String userToDel) {
        User u = getUserWithID(userID);
        if (u == null) {
            view.print("this should not be called");
        } else if (u.getFriends().contains(userToDel)) {
            u.deleteFriends(userToDel);
            if (this.getCurrUserID() == userID) {
            	view.print("Deleting " + userToDel + " from your friends list");
            }
        } else {
            view.print("No such friend found...");
        }
    }

    /**
     * Gets the role of the current user in this user manager
     * @return current user role if current user is defined, null otherwise
     */
    public String getCurrUserRole() {
        return currUser != null ? currUser.getRole() : null;
    }

    /**
     * Gets the ID of the current user in this user manager
     * @return current user ID if current user is defined, null otherwise
     */
    public String getCurrUserID() {
        return currUser != null ? currUser.getUserID() : null;
    }

    /**
     * Sign up a user for an event. Return true if the attendee is added to the event.
     * @param userID of the user that is being signed up for the event.
     * @param event that the user wants to sign up for.
     * @return true if the attendee is added to the event.
     */
    public void userEventSignUp(String userID, Event event, Room room) {
        if (!event.getAttendees().contains(userID) && room.getCapacity() > event.getEventOccupancy()) {
            event.addAttendee(userID);
            view.print("Signup successful");
        } else {
            view.print("Signup unsuccessful");
        }
    }

    /**
     * Remove a user from an event. Return true if the attendee is removed from the event.
     * @param userID of the user that is being removed from the event.
     * @param event that the user wants to be removed from.
     * @param room current fullness(occupation) of the room should be changed.
     * @return true if the attendee is removed from the event.
     */
    public boolean userEventCancel(String userID, Event event, Room room) {
        if (event.getAttendees().contains(userID)) {
            event.removeAttendee(userID);
            return true;
        } else {
            view.print("Removal unsuccessful");
            return false;
        }
    }

    public List<String> getCurrUserPerms() {
    	return (currUser == null) ? null : currUser.getPermissions();
    }
    
    public boolean getCurrUserLoggedIn() {
    	return currUser != null && currUser.getLoggedIn();
    }
}
