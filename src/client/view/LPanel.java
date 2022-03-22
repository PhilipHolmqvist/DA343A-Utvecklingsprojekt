package client.view;

import client.controller.ClientController;
import client.controller.ServerConnection;
import model.ServerUpdate;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LPanel extends JPanel {
    private ClientController controller;
    private int width;
    private int hight;

    private JList<String> connectedUsers;
    private JList<String> contacts;
    private JLabel text;
    private JButton addContact;
    private JButton removeContact;
    private DefaultListModel<String> contactList;

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
        text.setLocation(20, 350);
        text.setSize(90, 20);
        add(text);


        contactList = new DefaultListModel<>();
        contacts = new JList<>(contactList);
        contacts.setBorder(BorderFactory.createLoweredBevelBorder());
        contacts.setLocation(20, 370);
        contacts.setBackground(new Color(192, 192, 192));
        contacts.setSize(260, 230);
        add(contacts);

        addContact = new JButton("Lägg till");
        addContact.setLocation(20, 300);
        addContact.setSize(130, 40);
        add(addContact);

        removeContact = new JButton("Ta bort");
        removeContact.setLocation(150, 300);
        removeContact.setSize(130, 40);
        add(removeContact);

        addListeners();
    }

    public void setContacts(ArrayList<String> listOfContacts){
        String[] list = new String[listOfContacts.size()];
        for(int i = 0; i < listOfContacts.size(); i++){
            list[i] = listOfContacts.get(i);
        }
        contacts.setListData(list);
    }

    private void addListeners() {
        ActionListener listener = new ButtonActionListeners();
        addContact.addActionListener(listener);
        removeContact.addActionListener(listener);
    }

    public ArrayList<String> getSelectedRecipients(){
        ArrayList<String> selectedRecipients = new ArrayList<>();
        selectedRecipients.addAll(connectedUsers.getSelectedValuesList());
        selectedRecipients.addAll(contacts.getSelectedValuesList());
        return selectedRecipients;
    }

    public void serverUpdate(ServerUpdate update) {

        System.out.println(update.getConnectedList());
        System.out.println(update.getNewUserConnected());

        ArrayList<User> users = update.getConnectedList();
        User thisUser = controller.getServerConnection().getUser();

        String[] userlist = new String[users.size()];
        for(int i = 0; i < users.size(); i++){
            if(!users.get(i).getUsername().equals(thisUser.getUsername())){
                userlist[i] = users.get(i).getUsername();
            }

        }
        connectedUsers.setListData(userlist);
    }

    //Om en knapp trycks, anropa controller.
    class ButtonActionListeners implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == addContact) {
                contactList.addElement(connectedUsers.getSelectedValue());
                System.out.println("Lägger nu till " + connectedUsers.getSelectedValue() + " som en kontakt");
                controller.addContact(connectedUsers.getSelectedValue());
            }
            else if (e.getSource()== removeContact) {
                contactList.removeElement(connectedUsers.getSelectedValue());
                controller.removeContact(connectedUsers.getSelectedValue());
            }
        }
    }
}
