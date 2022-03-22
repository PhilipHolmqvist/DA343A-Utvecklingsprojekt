package model;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable {
    private String username;
    private Icon icon;
    private ArrayList<String> contacts;

    public User(){

    }

    public User(String username, Icon icon){
        this.username = username;
        this.icon = icon;
        this.contacts = new ArrayList<>();
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
        contacts.add(username);
    }

    public void removeContact(String username){
        contacts.remove(username);
    }
}
