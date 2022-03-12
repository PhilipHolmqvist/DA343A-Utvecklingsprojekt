package client.controller;

import client.view.MainFrame;
import model.Buffer;
import model.Message;
import model.User;

import javax.sound.sampled.Port;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class ClientController {
    private MainFrame view;

    public ClientController() throws IOException {
        view = new MainFrame(this);
        new ServerConnection("127.0.0.1", 721, this);

    }

    public static void main(String[] args) throws IOException {
        new ClientController();
    }
}
