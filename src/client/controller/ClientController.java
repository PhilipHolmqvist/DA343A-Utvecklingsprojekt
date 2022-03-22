package client.controller;

import client.view.LoginWindow;
import client.view.MainFrame;
import model.Message;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ClientController {
    private MainFrame view;
    private LoginWindow loginWindow;
    private User login;
    private ServerConnection serverConnection;

    public ClientController() throws IOException {
        LoginWindow loginWindow = new LoginWindow(new Frame("Logga in"));
        loginWindow.setVisible(true);

        if (loginWindow.authenticate()) {
            String username = loginWindow.getUsername();
            Icon icon = new ImageIcon(loginWindow.getImagePath());
            this.login = new User(username, icon);
            view = new MainFrame(this);
            serverConnection = new ServerConnection("127.0.0.1", 721, this, login, view.getMainPanel());

            view.setUser(login);
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) throws IOException {
        new ClientController();
        new ClientController();
        new ClientController();
    }

    public void clientDisconnecting() {
        serverConnection.clientDisconnecting();
    }

    public void sendMessage(String text, ArrayList<String> recipients) {
        Message msg = new Message();
        msg.setText(text);
        msg.setSender(serverConnection.getUser());
        msg.setRecipients(serverConnection.getUser().getUsersFromString(recipients));
        serverConnection.sendMessage(msg);
        view.displayNewMessage(msg);
    }

    public ServerConnection getServerConnection() {
        return serverConnection;
    }

    public void addContact(String selectedContactToAdd) {
        serverConnection.getUser().addContact(selectedContactToAdd);
        serverConnection.addContacts();
        serverConnection.sendUser();
    }

    public void removeContact(String selectedContactToRemove){
        serverConnection.getUser().removeContact(selectedContactToRemove);
        serverConnection.sendUser();
    }
}
