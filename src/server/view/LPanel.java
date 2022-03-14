package server.view;

import server.controller.ServerController;
import javax.swing.*;
import java.awt.*;

public class LPanel extends JPanel {
    private ServerController controller;
    private JTextField fromDate;

    public LPanel(ServerController controller, int width, int hight){
        this.controller = controller;
        this.setSize(width, hight);
        this.setLayout(null);
        this.setBackground(Color.GRAY);

        setUp();
    }

    private void setUp(){

        fromDate = new JTextField();
        fromDate.setSize(150, 20);
        fromDate.setLocation(20,20);
        add(fromDate);
    }
}
