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
        System.out.println("Startar strömmarna");
        new ClientInput(this).start();
        new ClientOutput().start();
    }

    public void newMsgForClient(Message msg){
        objectsToSend.put(msg);
    }

    public User getUser(){
        return user;
    }

    public void newServerUpdate(ServerUpdate update) {
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

                //Sedan kan klient bara skicka Message objekt.
                while(true){
                    Message msg = (Message) ois.readObject();
                    if(msg.getText().equals("//disconnet")){
                        controller.clientConnected(connection);
                    }
                    controller.sendMessage(msg);
                }


            }catch (Exception e){
                System.err.println(e);
            }
        }
    }

    private class ClientOutput extends Thread{
        private ObjectOutputStream oos;

        public void run(){
            try{
                oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                oos.writeObject(objectsToSend.get());

                while(true){
                    oos.writeObject(objectsToSend.get());
                }


            }catch (Exception e){
                System.err.println(e);
            }
        }
    }

}
