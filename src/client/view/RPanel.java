package client.view;

import client.controller.ClientController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class RPanel extends JPanel {
    private ClientController controller;
    private int width;
    private int height;
    private JTextArea chatWindow;
    private JTextArea writeMessageWindow;
    private JButton sendMessage;
    private JButton choosePic;
    private String imagePath;


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

        choosePic = new JButton("Välj bild");
        choosePic.setSize(120, 40);
        choosePic.setLocation(270, 545);

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
        writeMessageWindow.setLocation(40, 450);
        writeMessageWindow.setSize(500, 90);
        writeMessageWindow.setBorder(BorderFactory.createLoweredBevelBorder());
        writeMessageWindow.setBackground(new Color(255, 255, 255));
        writeMessageWindow.setEditable(true);
        add(writeMessageWindow);

        sendMessage = new JButton("Skicka meddelande");
        sendMessage.setSize(150, 40);
        sendMessage.setLocation(390,545);
        add(sendMessage);

    }
}
