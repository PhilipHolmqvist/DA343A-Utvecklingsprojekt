package client.controller;

import client.view.LoginWindow;
import client.view.MainFrame;
import model.Buffer;
import model.Message;
import model.User;

import javax.sound.sampled.Port;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Objects;

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
            String imagePath = loginWindow.getImagePath();
            Icon icon = new ImageIcon(loginWindow.getImagePath());
            login = new User(username, icon);

            view = new MainFrame(this);


            String server = loginWindow.getServerName();
            int port = loginWindow.getPort();
            serverConnection = new ServerConnection("127.0.0.1", 721, this, login, view.getMainPanel());

            login.setUsername(loginWindow.getUsername());
            login.setIcon(icon);
            view.setUser(login);
            //vet ej om detta funkar ^^^^?
        } else {
            System.exit(0);
        }
    }

    public User getUser(){
        return login;
    }

    public static void main(String[] args) throws IOException {
        new ClientController();
        new ClientController();
        new ClientController();
    }

    public void clientDisconnecting() {
        serverConnection.clientDisconnecting();
    }

    public void sendMessage(Message msg) {
        serverConnection.sendMessage(msg);
    }

    public ServerConnection getServerConnection() {
        return serverConnection;
    }

    public void addContact(List<String> selectedContactsToAdd) {
        for(int i = 0; i < selectedContactsToAdd.size(); i++){
            login.addContact(selectedContactsToAdd.get(i));
        }
        serverConnection.sendUser(login);
    }

    public void removeContact(List<String> selectedContactsToRemove){
        for(int i = 0; i < selectedContactsToRemove.size(); i++){
            login.removeContact(selectedContactsToRemove.get(i));
        }
        serverConnection.sendUser(login);
    }
}
