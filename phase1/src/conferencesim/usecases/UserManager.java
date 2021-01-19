package conferencesim.usecases;

import conferencesim.entities.User;
import conferencesim.entities.Event;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserManager implements Serializable {

    private List<User> userList;
    private User currUser = null;

    /**
     * Creates an empty UserManager.
     */
    public UserManager() {
        userList = new ArrayList<>();
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
    private User getUserWithID(String userID) {
        for (User u : userList) {
            if (userID.equals(u.getUserID())) return u;
        }
        System.out.println("[Error] Username not found: " + userID);
        return null;
    }

    /**
     * Get a list of all users registered
     * @return List object containing user entities
     */
    private List<User> getUserList() {
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
     * @return false if there is an existing user in the user list with the same user id as userID else, add the user to
     * the user list and return true.
     */
    public boolean registerUser(String userID, String password) {
        for (User user : this.userList) {
            if (user.getUserID().equals(userID)) {
                System.out.println("[Error] Duplicate IDs are not allowed");
                return false;
            }
        }
        User u = new User(userID, password);
        this.userList.add(u);
        System.out.println("[Notice] Registration complete");
        return true;
    }

    //Overloaded method with role input

    public boolean registerUser(String userID, String password, String role) {
        for (User user : this.userList) {
            if (user.getUserID().equals(userID)) {
                System.out.println("[Error] Duplicate IDs are not allowed");
                return false;
            }
        }
        User u = new User(userID, password);
        u.setRole(role);

        this.userList.add(u);
        System.out.println("[Notice] Registration complete");
        return true;
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
            return true;
        } else {
            System.out.println("[Error] No such user exists");
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
    public boolean loginUser(String userID, String password) {
        User u = getUserWithID(userID);
        if (u != null && password.equals(u.getPassword()) && !u.getLoggedIn()) {
            currUser = u;
            u.setLoggedIn(true);
            return true;
        }
        return false;
    }

    /**
     * Allows the user to log out. Return true if the user is logged in, then set current user to null and
     * log in status of the user to be false.
     * @param u user that is logged out from the system.
     * @return true if the user is logged in, then set current user to null and log in status of the user to be false.
     */
    public boolean logoutUser(User u) {
        if (u != null && u.getLoggedIn()) {
            currUser = null;
            u.setLoggedIn(false);
            return true;
        }
        return false;
    }

    /**
     * Add another user to the user's friend list. Return true if the user that is being added exists and does not
     * already exist in the user's friends list.
     * @param userID id of the user.
     * @param userToAdd user's friend that is going to be added.
     * @return true if the user that the user is trying to add exists and does not already exist in the user's friends
     * list.
     */
    public boolean addUserFriend(String userID, String userToAdd) {
        User u = getUserWithID(userID);
        if (u == null) return false;
        if (!u.getFriends().contains(userToAdd)) {
            u.addFriends(userToAdd);
            return true;
        } else {
            System.out.println("[Warning] Username already in messaging list, skipping...");
            return false;
        }
    }

    /**
     * Remove another user from user's friend list. Return true if the user in question exists and is present in this
     * user's friends list.
     * @param userID id of the user.
     * @param userToDel user's friend to be removed.
     * @return true if user to delete exists and is present in the user's friend list.
     */
    public boolean deleteUserFriend(String userID, String userToDel) {
        User u = getUserWithID(userID);
        if (u == null) return false;
        if (u.getFriends().contains(userToDel)) {
           u.deleteFriends(userToDel);
           return true;
        } else {
            System.out.println("No such friend found...");
            return false;
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
    public boolean userEventSignUp(String userID, Event event) {
        return event.addAttendee(userID);
    }

    /**
     * Remove a user from an event. Return true if the attendee is removed from the event.
     * @param userID of the user that is being removed from the event.
     * @param event that the user wants to be removed from.
     * @return true if the attendee is removed from the event.
     */
    public boolean userEventCancel(String userID, Event event) { return event.removeAttendee(userID);}

}
