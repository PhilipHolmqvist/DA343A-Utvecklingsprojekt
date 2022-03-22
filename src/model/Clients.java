package model;

import server.controller.ClientConnection;

import java.util.HashMap;

public class Clients {
    private HashMap<User, ClientConnection> clients;

    public Clients(){
        clients = new HashMap<>();
    }

    public synchronized void put (User user, ClientConnection client){
        clients.put(user, client);
    }

    public synchronized ClientConnection get (User user){
        return get(user);
    }
}
