package model;

import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Message implements Serializable {
    private String text;
    private Icon icon;
    private User sender;
    private String hourTime;
    private String dateTime;
    private ArrayList<String> recipients;

    public Message(String text, Icon icon){
        this.text = text;
        this.icon = icon;

        LocalDateTime currTime = LocalDateTime.now();
        int year = currTime.getYear();
        int month = currTime.getMonthValue();
        int day = currTime.getDayOfMonth();
        int hour = currTime.getHour();
        int minute = currTime.getMinute();
        int second = currTime.getSecond();
        this.dateTime = String.format("%d-%02d-%02d %02d:%02d", year, month, day, hour, minute);
        this.hourTime = String.format("%02d:%02d", hour, minute);
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

    public String getSender() {
        return sender.getUsername();
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public ArrayList<String> getRecipients() {return recipients;}

    public void setRecipients(ArrayList<String> recipients) {
        this.recipients = recipients;
    }

    public String getHourTime() {
        return hourTime;
    }

    public String getDateTime() {
        return dateTime;
    }
}
