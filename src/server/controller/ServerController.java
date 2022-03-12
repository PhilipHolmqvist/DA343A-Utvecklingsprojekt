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
        activeClients = new ArrayList<ClientConnection>();
        new Connection(721, this).start();
    }


    private class Connection extends Thread {
        private int port;
        private ServerController controller;
        private ClientConnection client;

        public Connection(int port, ServerController controller){
            this.port = port;
            this.controller = controller;
        }

        public void run() {
            Socket socket = null;
            System.out.println("Server startad");
            try (ServerSocket serverSocket = new ServerSocket(port)) { //Skapar en serverSocket med porten
                while(true) {
                    try {
                        socket = serverSocket.accept(); //När en klient kommer skapas en ny socket
                        System.out.println("ny klient accepterad");
                        new ClientConnection(socket); //en ny instans av clientHandler instanseras med socket som parameter.

                        //activeClients.add(client);
                        //System.out.println("ny klient tillagd som aktiv");
                        //ServerUpdate update = newUserConnected(client.getUser());
                       // for(int i = 0; i < activeClients.size(); i++){
                         //   activeClients.get(i).updateClient(update);
                        //}
                        //System.out.println("Uppdatering skickad");

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

    public static void main(String[] args) {

        new ServerController();
        //Startar servern
    }
}
