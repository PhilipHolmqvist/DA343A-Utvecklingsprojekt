package server.controller;

import model.*;
import server.view.MainFrame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

//Servern är en flertrådad server där varje klient hanteras av en tråd.
//När en client ansluter sig skapas en ny instans av klassen ClientConnection.
public class ServerController {
    private MainFrame view;
    private int port = 721;
    private UnsendMessages unsendMessages;
    private HashMap<User, ClientConnection> activeUsers;
    private HashMap<User, ArrayList<String>> userContactList;

    private ClientConnection latestClientConnected;
    private ServerUpdate serverUpdate = null;

    public ServerController(){
        this.view = new MainFrame(this, 900, 600);
        activeUsers = new HashMap<>();
        unsendMessages = new UnsendMessages();
        userContactList = new HashMap<>();
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
            System.out.println("Server, tråd #1 startad");
            try (ServerSocket serverSocket = new ServerSocket(port)) { //Skapar en serverSocket med porten
                while(true) {
                    try {
                        socket = serverSocket.accept(); //När en klient kommer skapas en ny socket
                        client = new ClientConnection(socket, controller); //en ny instans av clientHandler instanseras med socket som parameter.

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

    private ServerUpdate getUpdate() {
        return serverUpdate;
    }

    private void createAndSendServerUpdate(){
        ServerUpdate serverUpdate = new ServerUpdate();

        ArrayList<User> connectedList = new ArrayList<>();
        activeUsers.forEach(((user, clientConnection) -> connectedList.add(user)));

        serverUpdate.setConnectedList(connectedList);
        serverUpdate.setNewUserConnected(latestClientConnected.getUser());

        activeUsers.forEach(((user, clientConnection) -> clientConnection.newServerUpdate(serverUpdate)));
    }

    public void clientDisconnected(User user){
        activeUsers.remove(user);
        System.out.println(user.getUsername() + " discade.. :(");
        createAndSendServerUpdate();
    }

    public void clientConnected(ClientConnection client){
        User user = client.getUser();

        activeUsers.put(user, client);
        if(unsendMessages.contains(user)){
            ArrayList<Message> msgsForClient = unsendMessages.get(user);
            for(int i = 0; i < msgsForClient.size(); i++){
                client.newMsgForClient(msgsForClient.get(i));
            }
        }
        if(userContactList.containsKey(user)){
            ContactListUpdate clu = new ContactListUpdate(user, userContactList.get(user));
            client.newServerUpdate(clu);
        }
        latestClientConnected = client;
        createAndSendServerUpdate();
    }

    public void updateActiveClientUser(User user, ClientConnection clientConnection){
        System.out.println("Uppdaterade kontakterna för " + user.getUsername() + " med kontakterna " + user.getContacts());
        activeUsers.put(user, clientConnection);
        userContactList.put(user, user.getContacts());
        System.out.println("Server fick en uppdatering från klient. Detta las till");

    }

    public void sendMessage(Message msg){
        ArrayList<User> recipients = msg.getRecipients();
        System.out.println("Skickar msg:s");

        for(int i = 0; i < recipients.size(); i++){
            User reciver = recipients.get(i);
            if(activeUsers.containsKey(reciver)){
                activeUsers.get(reciver).newMsgForClient(msg);

            }else{
                unsendMessages.put(reciver, msg);
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

    public static void main(String[] args) {

        new ServerController();
        //Startar servern
    }


}
