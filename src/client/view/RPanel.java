package client.view;

import client.controller.ClientController;

import javax.swing.*;
import java.awt.*;

public class RPanel extends JPanel {
    private ClientController controller;


    public RPanel(ClientController controller, int widht, int hight){
        this.controller = controller;
        this.setSize(widht, hight);

        this.setBackground(Color.black);
    }
}
