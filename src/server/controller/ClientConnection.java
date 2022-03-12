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

    public ClientConnection(Socket socket){
        this.socket = socket;
        msgToSend = new Buffer<Message>();
        System.out.println("Startar strömmarna");
        new ClientInput().start();
        new ClientOutput().start();
    }

    public void newMsgForClient(Message msg){
        msgToSend.put(msg);
    }



    private class ClientInput extends Thread{
        private ObjectInputStream ois;

        public void run(){
            try{
                ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                User user = (User) ois.readObject();
                System.out.println(user.getUsername());


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
