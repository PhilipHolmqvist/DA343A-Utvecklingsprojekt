package model;

import java.util.ArrayList;

public class ServerUpdate {
    private User newUserConnected;
    private ArrayList<User> connectedList;

    public ServerUpdate(User newUserConnected, ArrayList<User> connectedList){
        this.newUserConnected = newUserConnected;
        this.connectedList = connectedList;
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
