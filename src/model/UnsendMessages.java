package model;

import java.util.ArrayList;
import java.util.HashMap;

public class UnsendMessages {
    private HashMap<User, ArrayList<Message>> unsedMessages;

    public UnsendMessages(){
        this.unsedMessages = new HashMap<>();
    }

    public synchronized void put(User user, Message message){
        if(unsedMessages.get(user) == null){
            ArrayList<Message> unsedMsg = new ArrayList<>();
            unsedMessages.put(user, unsedMsg);
        }
        ArrayList<Message> unsed = unsedMessages.get(user);
        unsed.add(message);
        unsedMessages.replace(user, unsed);
    }

    public synchronized ArrayList<Message> get(User user){
        ArrayList<Message> unsentMsgsToUser = unsedMessages.get(user);
        return unsentMsgsToUser;
    }

    public synchronized boolean contains(User user){
        return unsedMessages.containsKey(user);
    }

}
