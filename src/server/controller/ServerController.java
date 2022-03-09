package server.controller;

import model.User;
import server.view.MainFrame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//Servern är en flertrådad server där varje klient hanteras av en tråd.
//När en client ansluter sig skapas en ny instans av klassen ClientConnection.
public class ServerController {
    private MainFrame view;
    private User user;

    public ServerController(){
        this.view = new MainFrame(this, 900, 600);

        new Connection(422).start(); //Byt till korrekt port.
    }

    private class Connection extends Thread {
        private int port;

        public Connection(int port) {
            this.port = port;
        }

        //TODO
        // Connection ärver Thread och implementerar en run()-metod som skapar en ServerSocket som lyssnar på port
        // Vid en inkommande uppkoppling (socket.accept()) skapas ett nytt objekt av typ ClientConnection med port som anropsparameter.
        // Sedan lyssnar run()-metoden på nya uppkopplingar.
        public void run() {
            Socket socket = null;
            System.out.println("Server startad");
            try (ServerSocket serverSocket = new ServerSocket(port)) { //Skapar en serverSocket med porten
                while(true) {
                    try {
                        socket = serverSocket.accept(); //När en klient kommer skapas en ny socket
                        new ClientConnection(socket); //en ny instans av clientHandler instanseras med socket som parameter.
                    } catch(IOException e) {
                        System.err.println(e);
                        if(socket!=null)
                            socket.close();
                    }
                }
            } catch(IOException e) {
                System.err.println(e);
            }
            System.out.println("Server stoppad");
        }
    }



    public static void main(String[] args) {
        new ServerController();
    }
}
