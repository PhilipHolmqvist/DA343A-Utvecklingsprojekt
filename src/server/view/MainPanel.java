package server.view;

import server.controller.ServerController;

import javax.swing.*;

public class MainPanel extends JPanel {
    private ServerController controller;
    private LPanel leftPanel;
    private RPanel rightPanel;


    public MainPanel(ServerController controller, int width, int hight) {
        this.controller = controller;
        setSize(width, hight);
        setLayout(null);

        leftPanel = new LPanel(controller, width / 2, hight);
        leftPanel.setLocation(0,0);
        add(leftPanel);

        rightPanel = new RPanel(controller, width / 2, hight);
        rightPanel.setLocation(width/2, 0);
        add(rightPanel);

    }
}
