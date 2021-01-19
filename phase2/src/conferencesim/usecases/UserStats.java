package conferencesim.usecases;

import conferencesim.entities.userpack.User;
import java.util.List;

public class UserStats extends UserManager{

    List<User> userList;

    public UserStats(List<User> userList){
        this.userList = userList;
    }

    /**
     * Checks how many users have registered into the program
     * @return number of users registered.
     */
    public int usersRegistered() {
        int size = this.userList.size();
        view.print("There are " + size + " users registered");
        return size;
    }

    /**
     * Checks how many speakers have registered into the program
     * @return number of speakers registered.
     */
    public int speakersRegistered(){

        int size = 0;

        for(User u: this.userList){
            if (u.getRole().equals("Speaker"));
            size++;
        }
        view.print("There are " + size + " speakers registered");
        return size;

    }

    /**
     * Checks how many friends the user have in their friends list.
     * @return number of friends in the user's friends list
     */
    public int numFriends(User u) {

        view.print("There are " + u.getFriends().size() + " friends in your friends list");
        return u.getFriends().size();
    }
}

