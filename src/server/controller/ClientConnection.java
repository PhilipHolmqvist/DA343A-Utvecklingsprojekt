package server.controller;

import model.Buffer;
import model.Message;
import model.ServerUpdate;
import model.User;

import java.io.*;
import java.net.Socket;

public class ClientConnection {
    private Socket socket;
    private Buffer<Message> msgToSend;
    private User user;
    private ServerController controller;

    public ClientConnection(Socket socket, ServerController controller){
        this.socket = socket;
        this.controller = controller;
        msgToSend = new Buffer<Message>();
        System.out.println("Startar strömmarna");
        new ClientInput().start();
        new ClientOutput().start();
    }

    public void newMsgForClient(Message msg){
        msgToSend.put(msg);
    }

    public User getUser(){
        return user;
    }



    private class ClientInput extends Thread{
        private ObjectInputStream ois;

        public void run(){
            try{
                ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                //Första objektet som servern får ifrån Client är alltid ett user objekt.
                user = (User) ois.readObject();
                System.out.println(user.getUsername() + " anslöt sig.");

                //Sedan kan klient bara skicka Message objekt.
                while(true){
                    Message msg = (Message) ois.readObject();
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
                oos.writeObject(new ServerUpdate());

                while(true){
                    oos.writeObject(msgToSend.get());
                }


            }catch (Exception e){
                System.err.println(e);
            }
        }
    }

}
