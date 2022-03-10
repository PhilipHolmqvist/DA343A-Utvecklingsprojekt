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


        leftPanel = new LPanel();
        add(leftPanel);

        rightPanel = new RPanel();
        add(rightPanel);

    }
}
