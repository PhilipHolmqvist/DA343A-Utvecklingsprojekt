package server.controller;

import model.Buffer;
import model.Message;
import model.ServerUpdate;
import model.User;

import java.io.*;
import java.net.Socket;

public class ClientConnection extends Thread{
        private ServerController controller;
        private Socket socket;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;
        private User user;
        private Buffer<Message> messageBuffer;
        private Boolean firstConnect = true;

        public ClientConnection(Socket socket, ServerController controller) throws IOException {
            this.socket = socket;
            ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            this.messageBuffer = new Buffer<Message>();
            start();
        }

        public void addMessageToBuffer(Message msg){
            messageBuffer.put(msg);
        }

        public User getUser() {
            return user;
        }

        public void run() {

            try{
                while(true){

                if(firstConnect) {
                    Object obj = ois.readObject();
                    if (obj instanceof User) { //Ny användare har anslutit sig till servern
                        //Logga att en ny användare är online.
                        //Skicka uppdatering till Klienter att en ny användare är online.
                        this.user = (User) obj;
                    }
                }

                Message msg = (Message) ois.readObject();
                controller.newMessage(msg);

                }
            }catch (Exception e) {
                System.err.println(e);
            }
        }

    public void updateClient(ServerUpdate update) {
            try{
                oos.writeObject(update);
            }catch (Exception e){
                System.out.println(e);
            }
    }

    public void newMsgForClient(Message msg){
            try {
                oos.writeObject(msg);
            }catch (Exception e){
                System.out.println(e);
            }
    }
}
