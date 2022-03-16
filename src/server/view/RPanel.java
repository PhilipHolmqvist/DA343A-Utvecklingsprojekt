package server.view;

import server.controller.ServerController;

import javax.swing.*;
import java.awt.*;

public class RPanel extends JPanel {
    private ServerController controller;
    private JList<String> outputLogs;

    //width 450
    //hight 600

    public RPanel(ServerController controller, int width, int hight){
        setSize(width, hight);
        setLayout(null);
        setUp();
    }

    private void setUp(){

        outputLogs = new JList<>();
        outputLogs.setBorder(BorderFactory.createLoweredBevelBorder());
        outputLogs.setLocation(0, 40);
        outputLogs.setBackground(new Color(187, 187, 187));
        outputLogs.setSize(390, 480);
        add(outputLogs);
    }

    public void displayLogInView(String[] logs) {
        outputLogs.setListData(logs);
    }
}
