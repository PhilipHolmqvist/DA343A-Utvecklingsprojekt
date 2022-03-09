package client.controller;

import client.view.MainFrame;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClientController {
    private MainFrame view;

    public ClientController(){
        view = new MainFrame(this);

    }



    private class Client extends Thread{
        private ObjectInputStream ois;
        private ObjectOutputStream oos;

        //Körs när client startar
        public void run(){
            try{
                oos.writeObject("g"); //Vid anslutning identifierar sig användaren med användarnamn och bild.


            } catch (Exception e) {
                    e.printStackTrace();
            }

            while(true){
                try{
                    oos.writeObject("g"); //Vänta på att anv skriver ett meddelande.


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }




    public static void main(String[] args) {
        new ClientController();
    }
}
