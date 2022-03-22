package client.view;

import client.controller.ClientController;
import client.controller.ServerConnection;
import model.Message;
import model.User;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RPanel extends JPanel {
    private ClientController controller;
    private LPanel leftpanel;
    private int width;
    private int height;
    private JTextArea chatWindow;
    private JTextArea writeMessageWindow;
    private JButton sendMessage;
    private JButton choosePic;
    private String imagePath;
    private JLabel userPic;
    private JLabel chatPic;
    private JLabel username;
    private JLabel chatbuddyname;

    public RPanel(ClientController controller, LPanel leftpanel, int width, int height) {
        this.controller = controller;
        this.leftpanel = leftpanel;
        this.setSize(width, height);
        this.setLayout(null);
        setUp();
    }

    private void setUp() {
        chatWindow = new JTextArea();
        chatWindow.setLocation(120, 40);
        chatWindow.setSize(500, 400);
        chatWindow.setBorder(BorderFactory.createLoweredBevelBorder());
        chatWindow.setBackground(new Color(255, 255, 255));
        chatWindow.setEditable(false);
        add(chatWindow);

        choosePic = new JButton("Välj bild");
        choosePic.setSize(220, 40);
        choosePic.setLocation(250, 545);

        choosePic.addActionListener( e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(false);
            fileChooser.setCurrentDirectory(new java.io.File("./images"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg & png", "png", "jpg");
            fileChooser.setFileFilter(filter);

            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                imagePath = fileChooser.getSelectedFile().getAbsolutePath();
            }
        });

        add(choosePic);


        writeMessageWindow = new JTextArea();
        writeMessageWindow.setLocation(120, 450);
        writeMessageWindow.setSize(500, 90);
        writeMessageWindow.setBorder(BorderFactory.createLoweredBevelBorder());
        writeMessageWindow.setBackground(new Color(255, 255, 255));
        writeMessageWindow.setEditable(true);
        add(writeMessageWindow);

        sendMessage = new JButton("Skicka meddelande");
        sendMessage.setSize(150, 40);
        sendMessage.setLocation(470,545);
        add(sendMessage);

        ImageIcon imageIcon = new ImageIcon("images/angry.png"); // load the image to a imageIcon
        Image image = imageIcon.getImage(); // transform it
        Image newimg = image.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon = new ImageIcon(newimg);  // transform it back

        userPic = new JLabel(imageIcon);
        userPic.setSize(40,40);
        userPic.setLocation(40, 100);
        add(userPic);

        ImageIcon imageIcon2 = new ImageIcon("images/angry.png"); // load the image to a imageIcon
        Image image2 = imageIcon2.getImage(); // transform it
        Image newimg2 = image2.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        imageIcon2 = new ImageIcon(newimg2);  // transform it back

        userPic = new JLabel(imageIcon2);
        userPic.setSize(60,60);
        userPic.setLocation(40, 480);
        add(userPic);

        username = new JLabel("");
        username.setSize(90, 30);
        username.setLocation(40, 450);
        add(username);

        chatbuddyname = new JLabel();
        chatbuddyname.setSize(90, 30);
        chatbuddyname.setLocation(40, 70);
        add(chatbuddyname);

        addListeners();
    }

    public void setUserPic(ImageIcon icon){
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        icon = new ImageIcon(newimg);  // transform it back
        userPic.setIcon(icon);
        repaint();
    }

    public void setChatPic(ImageIcon icon){
        Image image = icon.getImage(); // transform it
        Image newimg = image.getScaledInstance(60, 60,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
        icon = new ImageIcon(newimg);  // transform it back
        chatPic.setIcon(icon);
        repaint();
    }

    public void setUser(User login) {
        setUserPic((ImageIcon) login.getIcon());
        username.setText(login.getUsername());
    }

    public void setChatBuddy(User user){
        setChatPic((ImageIcon) user.getIcon());
        chatbuddyname.setText(user.getUsername());
    }

    private void addListeners() {
        ActionListener listener = new ButtonActionListeners();
        sendMessage.addActionListener(listener);
    }

    public void newMessage(Message msg) {
        String text = String.format("%s %s skriver: %s \n", msg.getHourTime(), msg.getSender(), msg.getText());
        chatWindow.append(text);
    }

    class ButtonActionListeners implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == sendMessage) {
                controller.sendMessage(writeMessageWindow.getText(), leftpanel.getSelectedRecipients());
                writeMessageWindow.setText("");



            }
        }
    }
}
