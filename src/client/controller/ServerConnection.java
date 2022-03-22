package client.controller;

import client.view.MainPanel;
import model.*;

import java.io.*;
import java.net.Socket;

public class ServerConnection{
    private ClientController controller;
    private Socket socket;
    private Buffer<Object> messagesToServer;
    private User user;
    private MainPanel view;

    //Klassen ServerConnection är klientens anslutning till servern. Den använder
    //två separata trådar, en för input och en för output. Detta för att hela tiden kunna
    //skicka meddelanden och samtidigt ta emot server uppdateringar samt meddelanden.
    public ServerConnection(String ip, int port, ClientController controller, User user, MainPanel view) throws IOException {
        this.controller = controller;
        this.view = view;
        this.user = user;
        messagesToServer = new Buffer<Object>();
        socket = new Socket(ip, port);
        new ClientOutput().start();
        new ClientInput().start();
    }

    public User getUser(){
        return user;
    }

    public void sendMessage(Message msg){
        messagesToServer.put(msg);
    }

    public void clientDisconnecting() {
        Message msg = new Message();
        msg.setText("//disconnect");
        messagesToServer.put(msg);
    }

    public void sendUser(){
        messagesToServer.put(user);
    }

    public void addContacts(){
        this.user.addContact("Johan");
    }


    //Skickar meddelande till servern
    private class ClientOutput extends Thread{
        private ObjectOutputStream oos;

        public void run(){
            try {
                oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                oos.writeObject(user);
                oos.flush();
                System.out.println("Klient skickade sin användare till server");

                while(true){
                    //Väntar tills dess att tråden blir notifierad av bufferten.
                    //Den blir notifierad då det finns ett message i bufferten att hämta.
                    Object obj = messagesToServer.get();
                    if(obj instanceof Message){
                        Message msg = (Message) obj;
                        if(msg.getText().equals("//disconnect")){
                            oos.writeObject(msg);
                            oos.flush();
                            System.exit(0);
                        }
                    }
                    oos.writeObject(obj);
                    oos.flush();

                }


            }catch (Exception e){
                System.err.println(e);
            }

        }

    }

    private class ClientInput extends Thread{
        private ObjectInputStream ois;

        public void run(){
            try{
                ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                while(true){
                    Object obj = ois.readObject();
                    System.out.println("tog emot");

                    if(obj instanceof ServerUpdate){
                        //Ny serverupdate. Packa upp den och visa i view.
                        System.out.println("Clienten fick en serveruppdatering!");
                        ServerUpdate update = (ServerUpdate) obj;
                        view.serverUpdate(update);
                    }
                    if(obj instanceof ContactListUpdate){
                        //Clienten fick veta vilka contakter den har
                        System.out.println("Clienten fick en kontaktuppdatering!");
                        ContactListUpdate update = (ContactListUpdate) obj;
                        System.out.println(update.getContacts());
                        user.setContacts(update.getContacts());
                        view.setContacts(update.getContacts());
                    }

                    if(obj instanceof Message){
                        //Nytt meddelande. Displaya det i view.
                        System.out.println("Clienten fick ett nytt Message!");
                        Message msg = (Message) obj;
                        view.displayMessage(msg);
                    }
                }

            }catch (Exception e) {
                e.printStackTrace();
            }



        }
    }
}
