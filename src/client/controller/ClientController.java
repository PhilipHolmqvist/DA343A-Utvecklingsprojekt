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
    private Socket socket;
    private int port;
    private String ip;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private User user = new User("Philip", null);

    public ClientController() {
        view = new MainFrame(this);
        this.ip = "127.0.0.1";
        this.port = 721;

        System.out.println("klient");
        try {
            connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect() throws IOException {
        socket = new Socket(ip, port);
        ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
        oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        System.out.println("hej");
        oos.writeObject(user);
        System.out.println("Klient har skrivit till server");
        new Client().start();
    }

    public void reConnect() {

    }

    public void disconnect() throws IOException {
        socket.close();
    }

    public void sendButtonPressed() {
        Message msg = new Message("Hejsan", null);

        try {
            oos.writeObject(msg);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    //ee
    //eeeeeeee eee ee

    private class Client extends Thread {
        private ObjectInputStream ois;
        private ObjectOutputStream oos;

        //Körs när client startar
        public void run(){
                try{
                    System.out.println("klient startad");
                    while(true) {
                        Message msg = (Message) ois.readObject();
                        view.displayNewMessage(msg);

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
