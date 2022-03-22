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
    private ClientConnection latestClientConnected;
    private ServerUpdate serverUpdate = null;
    private Logger logger;

    public ServerController(){
        this.view = new MainFrame(this, 900, 600);
        this.logger = new Logger();
        activeClients = new ArrayList<ClientConnection>();
        logger.log("Server online");
        new Connection(721, this).start();
        new UpdateHandeler(this).start();
    }

    public void sendMessage(Message msg){
        ArrayList<String> recipients = msg.getRecipients();

        for(int i = 0; i < activeClients.size(); i++){
            for(int j = 0; j < recipients.size(); j++){
                if(activeClients.get(i).getUser().getUsername().equals(recipients.get(j))){
                    activeClients.get(i).newMsgForClient(msg);
                    logger.log("Msg från " + msg.getSender() + " till " + msg.getRecipients());
                }
            }
        }
    }

    //Anropas när ok knappen blir tryckt. Parametern är datum och tid
    //i formatet yyyymmdd-tttt. Loggen ska hämtas från datum och tid till datum och tid.
    //skicka sedan en string array med alla loggar mellan dessa två tidpunkter.
    public void displayLog(String fromDate, String toDate) {
        String[] logs = new String []{
                        "20220316 13:40 Server online",
                        "20220316 13:41 Emma är online",
                        "20220316 13:41 Philip är online",
                        "20220316 13:42 från Emma till Philip: 'Hej!' ",
                        "20220316 13:42 från Philip till Emma: 'Tjenare!' ",
                        "20220316 13:43 Emma är offline",
                        "20220316 13:43 Philip är offline",};


        view.displayLogInView(logs);
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
            System.out.println("Server, tråd #1 startad");
            logger.log("Server online.");
            try (ServerSocket serverSocket = new ServerSocket(port)) { //Skapar en serverSocket med porten
                while(true) {
                    try {
                        socket = serverSocket.accept(); //När en klient kommer skapas en ny socket
                        System.out.println("ny klient accepterad");
                        client = new ClientConnection(socket, controller); //en ny instans av clientHandler instanseras med socket som parameter.
                        activeClients.add(client);


                    } catch(IOException e) {
                        System.err.println(e);
                    }
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
            System.out.println("Server stoppad");
        }
    }

    private class UpdateHandeler extends Thread {
        private ServerController controller;
        private ServerUpdate update;

        public UpdateHandeler(ServerController controller) {
            this.controller = controller;
        }

        public void run() {
            System.out.println("Server, tråd #2 startad");
            while (true) {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ServerUpdate newUpdate = controller.getUpdate();
                if(newUpdate != update){
                    this.update = newUpdate;
                    System.out.println("Update");
                    logger.log("Server skickade en uppdatering till samtliga klienter.");
                    controller.sendServerUpdateToClients(update);
                }
            }
        }
    }

    private void sendServerUpdateToClients(ServerUpdate update) {
        for(int i = 0; i < activeClients.size(); i++){
            activeClients.get(i).newServerUpdate(update);
        }
    }

    private ServerUpdate getUpdate() {
        return serverUpdate;
    }

    private void createServerUpdate(){
        ServerUpdate serverUpdate = new ServerUpdate();

        ArrayList<User> connectedList = new ArrayList<>();
        for(int i = 0; i < activeClients.size(); i++){
            connectedList.add(activeClients.get(i).getUser());
        }

        serverUpdate.setConnectedList(connectedList);
        serverUpdate.setNewUserConnected(latestClientConnected.getUser());

        this.serverUpdate = serverUpdate;
    }

    public void clientDisconnected(ClientConnection client){
        for(int i = 0; i < activeClients.size(); i++){
            if(activeClients.get(i) == client){
                activeClients.remove(i);
            }
        }
        createServerUpdate();
    }

    public void clientConnected(ClientConnection client){
        this.latestClientConnected = client;
        logger.log("Ny klient online: " + client.getUser().getUsername());
        createServerUpdate();
    }

    public void updateActiveClientUser(User user){
        for(int i = 0; i < activeClients.size(); i++){
            if(activeClients.get(i).getUser().getUsername().equals(user.getUsername())){
                activeClients.get(i).setUser(user);
            }
        }
        createServerUpdate();
    }

    public static void main(String[] args) {

        new ServerController();
        //Startar servern
    }


}
