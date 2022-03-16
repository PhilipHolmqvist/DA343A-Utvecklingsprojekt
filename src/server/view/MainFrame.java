package server.view;

import server.controller.ServerController;

import javax.swing.*;

public class MainFrame extends JFrame {
    private MainPanel mainPanel;
    private ServerController controller;

    public MainFrame(ServerController controller, int width, int height) {
        super("Server");
        this.setResizable(false);
        this.setSize(width, height);
        this.mainPanel = new MainPanel(controller, width, height);
        this.setContentPane(mainPanel);
        this.setVisible(true);
        this.controller = controller;   //Skapar basfönster

    }

    public void displayLogInView(String[] logs) {
        mainPanel.displayLogInView(logs);
    }
}
