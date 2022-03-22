package server.controller;

import model.Buffer;
import model.Message;
import model.ServerUpdate;
import model.User;

import java.io.*;
import java.net.Socket;

public class ClientConnection {
    private Socket socket;
    private Buffer<Object> objectsToSend;
    private User user;
    private ServerController controller;

    public ClientConnection(Socket socket, ServerController controller){
        this.socket = socket;
        this.controller = controller;
        objectsToSend = new Buffer<Object>();
        new ClientInput(this).start();
        new ClientOutput().start();
    }

    public void newMsgForClient(Message msg){
        objectsToSend.put(msg);
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public void newServerUpdate(Object update) {
        objectsToSend.put(update);
    }


    private class ClientInput extends Thread{
        private ObjectInputStream ois;
        private ClientConnection connection;

        public ClientInput(ClientConnection connection){
            this.connection = connection;
        }

        public void run(){
            try{
                ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                //Första objektet som servern får ifrån Client är alltid ett user objekt.
                user = (User) ois.readObject();
                System.out.println(user.getUsername() + " anslöt sig.");
                controller.clientConnected(connection);

                //Sedan kan klient bara skicka Message objekt.
                while(true){
                    Object obj = ois.readObject();

                    if(obj instanceof Message){
                        Message msg = (Message) obj;
                        if(msg.getText().equals("//disconnect")){
                            controller.clientDisconnected(user);
                            System.out.println("En klient disconnetar!");

                        }else{
                            controller.sendMessage(msg);
                        }
                    }

                    if(obj instanceof User){
                        //Jämför namnet med aktiva klienter. Där det matchar uppdatera den klientens kontakter.
                        User user23 = (User) obj;
                        System.out.println(user23.getContacts());
                        controller.updateActiveClientUser(user23, connection);
                    }

                }


            }catch (Exception e){
                e.printStackTrace();
                controller.clientDisconnected(user);
            }
        }
    }

    private class ClientOutput extends Thread{
        private ObjectOutputStream oos;

        public void run(){
            try{
                oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                while(true){
                    oos.writeObject(objectsToSend.get());
                    oos.flush();
                    System.out.println("Skickat något till klient");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
