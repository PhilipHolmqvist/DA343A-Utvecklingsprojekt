package model;

import javax.swing.*;

public class Message {
    private String text;
    private Icon icon;
    private User sender;
    private User[] recipients;

    public Message(String text, Icon icon){
        this.text = text;
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User[] getRecipients() {
        return recipients;
    }

    public void setRecipients(User[] recipients) {
        this.recipients = recipients;
    }
}
