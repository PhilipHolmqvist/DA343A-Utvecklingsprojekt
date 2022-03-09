package server.controller;

import model.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientConnection extends Thread{
        private Socket socket;
        private ObjectInputStream ois;
        private ObjectOutputStream oos;

        public ClientConnection(Socket socket) throws IOException {
            this.socket = socket;
            ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            start();
        }

        public void run() {

            try{
                while (true){
                    Message msg = (Message) ois.readObject();

                }
            }catch (Exception e) {
                System.err.println(e);
            }
        }
}
