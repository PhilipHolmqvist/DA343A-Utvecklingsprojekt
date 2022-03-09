package server.view;

import server.controller.ServerController;

import javax.swing.*;

public class MainPanel extends JPanel {
    private ServerController controller;


    public MainPanel(ServerController controller, int width, int hight) {
        this.setSize(width, hight);
        this.controller = controller;

    }
}
