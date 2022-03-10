package client.controller;

import client.view.MainFrame;
import model.User;

import javax.sound.sampled.Port;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class ClientController {
    private MainFrame view;
    private Socket socket;
    private int port;
    private String ip;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private User user;

    public ClientController(){
        view = new MainFrame(this);
        this.ip = "127.0.0.1";
        this.port = 721;
    }

    public void connect() throws IOException {
        socket = new Socket(ip, port);
        ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        oos.writeObject(user);
    }

    public void reConnect() {

    }

    public void disconnect() throws IOException {
        socket.close();
    }
    //ee

    private class Client extends Thread {
        private ObjectInputStream ois;
        private ObjectOutputStream oos;

        //Körs när client startar
        public void run(){
                try{
                    while(true) {
                        
                        oos.writeObject("g"); //Vänta på att anv skriver ett meddelande.
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }







    public static void main(String[] args) {
        new ClientController();
    }
}
