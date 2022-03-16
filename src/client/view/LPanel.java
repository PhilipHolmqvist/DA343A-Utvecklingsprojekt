package client.view;

import client.controller.ClientController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LPanel extends JPanel {
    private ClientController controller;
    private int width;
    private int hight;

    private JList<String> connectedUsers;
    private JList<String> contacts;
    private JLabel text;
    private JButton sendButton;

    public LPanel(ClientController controller, int width, int higth){
        this.controller = controller;
        this.setSize(width, higth);
        this.setLayout(null);
        setUp();

    }

    private void setUp(){
        text = new JLabel("Online: ");
        text.setLocation(20, 20);
        text.setSize(90, 20);
        add(text);

        connectedUsers = new JList<>();
        connectedUsers.setBorder(BorderFactory.createLoweredBevelBorder());
        connectedUsers.setLocation(20, 40);
        connectedUsers.setBackground(new Color(192, 192, 192));
        connectedUsers.setSize(260, 210);
        add(connectedUsers);

        text = new JLabel("Kontakter: ");
        text.setLocation(20, 290);
        text.setSize(90, 20);
        add(text);

        contacts = new JList<>();
        contacts.setBorder(BorderFactory.createLoweredBevelBorder());
        contacts.setLocation(20, 310);
        contacts.setBackground(new Color(192, 192, 192));
        contacts.setSize(260, 230);
        add(contacts);

        sendButton = new JButton("Send");
        sendButton.setSize(70, 40);
        sendButton.setLocation(20,560);
        add(sendButton);
    }

    private void addListeners() {
        ActionListener listener = new ButtonActionListeners();

        sendButton.addActionListener(listener);
    }

    class ButtonActionListeners implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == sendButton) {
                //controller.sendButtonPressed();
            }
        }
    }
}
