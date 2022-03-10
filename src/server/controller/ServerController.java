package server.controller;

import model.ServerUpdate;
import model.User;
import server.view.MainFrame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

//Servern är en flertrådad server där varje klient hanteras av en tråd.
//När en client ansluter sig skapas en ny instans av klassen ClientConnection.
public class ServerController {
    private MainFrame view;
    private ArrayList<User> connectedUsers;
    private int port = 721;

    public ServerController(){
        this.view = new MainFrame(this, 900, 600);
        new Connection(721, this).start();
        connectedUsers = new ArrayList<User>();
    }

    private class Connection extends Thread {
        private int port;
        private ServerController controller;
        private ArrayList<ClientConnection> activeClients = new ArrayList<>();

        public Connection(int port, ServerController controller) {

            this.port = port;
            this.controller = controller;
        }

        public ArrayList<ClientConnection> getActiveClients(){
            return activeClients;
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
                        ClientConnection client = new ClientConnection(socket, controller); //en ny instans av clientHandler instanseras med socket som parameter.
                        activeClients.add(client);
                        newUserConnected(client.getUser());

                    } catch(IOException e) {
                        System.err.println(e);
                    }
                }
            } catch(IOException e) {
                System.err.println(e);
            }
            System.out.println("Server stoppad");
        }
    }

    public void newUserConnected(User user){
        ServerUpdate update = new ServerUpdate(user, connectedUsers);
        connectedUsers.add(user);
    }



    public static void main(String[] args) {

        new ServerController();
        //Startar servern
    }
}
