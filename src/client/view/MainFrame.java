package client.view;

import client.controller.ClientController;
import client.controller.ServerConnection;
import model.Message;
import model.User;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    private MainPanel mainPanel;
    private ClientController controller;
    private final int width = 1000;
    private final int hight = 660;

    public MainFrame(ClientController controller) {
        super("Chat Client");
        this.setResizable(false);
        this.setSize(width, hight);
        this.mainPanel = new MainPanel(controller, width, hight);
        this.setContentPane(mainPanel);
        this.setVisible(true);
        this.controller = controller;   //Skapar basfönster

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                controller.clientDisconnecting();
                System.out.println("Client vill avsluta");

            }
        });

    }

    public MainPanel getMainPanel(){
        return mainPanel;
    }



    public void displayNewMessage(Message msg) {
        System.out.println(msg.getText());
    }

    public void setUser(User login) {
        mainPanel.setUser(login);
    }
}
