package conferencesim.entities.userpack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class User implements Serializable {
    protected String userID;  //deleted UserName from phase1
    protected String password;
    // if we create subclasses of user, store this list in the subclasses
    protected boolean loggedIn;
    protected String role;

    private List<String> friends = new ArrayList<String>();
    
    protected List<String> permList;

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

    public boolean getLoggedIn() {
        return loggedIn;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public String getRole(){
        return role;
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

    public abstract List<String> getPermissions();
}
