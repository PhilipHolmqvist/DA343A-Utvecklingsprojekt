package server.controller;

import model.Message;
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
    private int port = 721;
    private ArrayList<ClientConnection> activeClients;

    public ServerController(){
        this.view = new MainFrame(this, 900, 600);
        new Connection(721, this).start();
        activeClients = new ArrayList<ClientConnection>();
    }

    public void newMessage(Message msg) {
        User[] recipiants = msg.getRecipients();

        for(int i = 0; i < activeClients.size(); i++){
            for(int j = 0; j < recipiants.length; j++){
                if(activeClients.get(i).getUser() == recipiants[j]){
                    activeClients.get(i).newMsgForClient(msg);
                    break;
                }
            }
        }
    }

    private class Connection extends Thread {
        private int port;
        private ServerController controller;

        public Connection(int port, ServerController controller) {
            this.port = port;
            this.controller = controller;
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
                        ServerUpdate update = newUserConnected(client.getUser());
                        for(int i = 0; i < activeClients.size(); i++){
                            activeClients.get(i).updateClient(update);
                        }

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

    public ServerUpdate newUserConnected(User user){
        ArrayList<User> connectedUsers = new ArrayList<User>();
        for(int i = 0; i < activeClients.size(); i++){
            connectedUsers.set(i, activeClients.get(i).getUser());
        }
        ServerUpdate update = new ServerUpdate(user, connectedUsers);
        return update;
    }



    public static void main(String[] args) {

        new ServerController();
        //Startar servern
    }
}
