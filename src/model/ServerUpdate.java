package model;

import java.io.Serializable;
import java.util.ArrayList;

public class ServerUpdate implements Serializable {
    private User newUserConnected;
    private ArrayList<User> connectedList;

    public ServerUpdate(){

    }

    public User getNewUserConnected() {
        return newUserConnected;
    }

    public void setNewUserConnected(User newUserConnected) {
        this.newUserConnected = newUserConnected;
    }

    public ArrayList<User> getConnectedList() {
        return connectedList;
    }

    public void setConnectedList(ArrayList<User> connectedList) {
        this.connectedList = connectedList;
    }
}
