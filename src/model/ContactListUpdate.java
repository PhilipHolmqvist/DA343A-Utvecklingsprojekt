package model;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactListUpdate implements Serializable {
    private User belongsToUser;
    private ArrayList<String> contacts;

    public ContactListUpdate(User user, ArrayList<String> contacts){
        this.belongsToUser = user;
        this.contacts = contacts;
    }

    public ArrayList<String> getContacts(){
        return contacts;
    }
}
