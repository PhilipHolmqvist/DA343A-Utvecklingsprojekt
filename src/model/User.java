package model;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable {
    private String username;
    private Icon icon;
    private ArrayList<String> contacts;

    public User(String username){
        this.username = username;
        this.icon = null;
        this.contacts = new ArrayList<>();
    }

    public User(String username, Icon icon){
        this.username = username;
        this.icon = icon;
        this.contacts = new ArrayList<>();
    }

    public int hashCode(){
        return username.hashCode();
    }

    public boolean equals(Object obj) {
        if(obj!=null && obj instanceof User)
            return username.equals(((User)obj).getUsername());
        return false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public void addContact(String username){
        this.contacts.add(username);
    }

    public void removeContact(String username){
        contacts.remove(username);
    }

    public ArrayList<String> getContacts(){
        return contacts;
    }

    public void setContacts(ArrayList<String> contactlist){
        this.contacts = contactlist;
    }

    public ArrayList<User> getUsersFromString(ArrayList<String> userStrings){
        ArrayList<User> users = new ArrayList<>();
        for(int i = 0; i < userStrings.size(); i++){
            users.add(new User(userStrings.get(i)));
        }
        return users;
    }

    public String toString(){
        return String.format("User: " + username);
    }
}
