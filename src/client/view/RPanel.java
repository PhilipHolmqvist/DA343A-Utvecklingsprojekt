package client.view;

import client.controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class RPanel extends JPanel {
    private ClientController controller;
    private int width;
    private int height;
    private JTextArea chatWindow;
    private JTextArea writeMessageWindow;
    private JButton sendMessage;


    public RPanel(ClientController controller, int width, int height){
        this.controller = controller;
        this.setSize(width, height);
        this.setLayout(null);
        setUp();
    }

    private void setUp() {
        chatWindow = new JTextArea();
        chatWindow.setLocation(40, 40);
        chatWindow.setSize(500, 400);
        chatWindow.setBorder(BorderFactory.createLoweredBevelBorder());
        chatWindow.setBackground(new Color(255, 255, 255));
        chatWindow.setEditable(false);
        add(chatWindow);

        writeMessageWindow = new JTextArea();
        writeMessageWindow.setLocation(40, 450);
        writeMessageWindow.setSize(500, 90);
        writeMessageWindow.setBorder(BorderFactory.createLoweredBevelBorder());
        writeMessageWindow.setBackground(new Color(255, 255, 255));
        writeMessageWindow.setEditable(true);
        add(writeMessageWindow);

        sendMessage = new JButton("Skicka meddelande");
        sendMessage.setSize(150, 40);
        sendMessage.setLocation(390,560);
        add(sendMessage);
    }
}
