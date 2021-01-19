package conferencesim.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

    private String userID; //unique piece of string for each user
    private String username; // the name you choose to go by i.e. there can be two John smiths in quercus but their
                            // but their utorids would be different.
    private String password;
    // if we create subclasses of user, store this list in the subclasses
    private List<String> friends = new ArrayList<String>();
    private String role;
    private boolean loggedIn;

    /**
     * We require the userID and password in order to create an instance of User
     * @param userID used to create an instance of User.
     * @param password used to create an instance of User.
     */
    public User(String userID, String password){
        this.userID = userID;
        this.password = password;
        this.loggedIn = false;
    }

    public String getUserID(){
        return userID;
    }

    public String getPassword(){
        return password;
    }

    public String getRole() {
        return this.role;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Should not let role be set to anything other than Attendee, Organizer, Speaker.
    public void setRole(String role) {
        this.role = role;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * If the friends list does not contain the userID, add the userID to the friends list.
     * @param userID user id that is being added as a friend.
     * @return false if the friends list contains the userID else, return true.
     */
    public boolean addFriends(String userID){
        if(friends.contains(userID)){
            return false;
        }
        friends.add(userID);
        return true;
    }

    public boolean deleteFriends(String userID) {
        if (friends.contains(userID)) {
            friends.remove(userID);
            return true;
        }
        return false;
    }

    public List<String> getFriends(){
        return friends;
    }
}
