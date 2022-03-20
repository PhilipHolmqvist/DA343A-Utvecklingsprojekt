package client.view;

import client.controller.ClientController;
import model.Message;

import javax.swing.*;

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

    }

    public void displayNewMessage(Message msg) {
        System.out.println(msg.getText());
    }
}
