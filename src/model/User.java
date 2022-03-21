package model;

import javax.swing.*;
import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private Icon icon;
    private String contacts;

    public User(){

    }

    public User(String username, Icon icon){
        this.username = username;
        this.icon = icon;
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
}
