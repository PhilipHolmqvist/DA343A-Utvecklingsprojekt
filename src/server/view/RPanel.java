package server.view;

import server.controller.ServerController;

import javax.swing.*;
import java.awt.*;

public class RPanel extends JPanel {
    private ServerController controller;

    public RPanel(ServerController controller, int width, int hight){
        setSize(width, hight);
        setBackground(Color.blue);
    }
}
