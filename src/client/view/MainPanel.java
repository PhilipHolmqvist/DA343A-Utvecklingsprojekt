package client.view;

import client.controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private ClientController controller;
    private int width;
    private int hight;

    private LPanel leftPanel; //Kontakter, användare online etc.
    private RPanel rightPanel; //Chatt och möjlighet att skriva.

    public MainPanel(ClientController controller, int width, int hight) {
        this.width = width;
        this.hight = hight;
        this.controller = controller;

        this.setSize(width, hight);
        this.setLayout(null);
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        setUp();
    }

    public void setUp(){
        leftPanel = new LPanel(controller, width / 3, hight);
        leftPanel.setLocation(0,0);
        add(leftPanel);

        rightPanel = new RPanel(controller, 600, hight);
        rightPanel.setLocation(width/3, 0);
        add(rightPanel);


    }
}
