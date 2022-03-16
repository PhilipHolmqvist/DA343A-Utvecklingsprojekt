package server.view;

import server.controller.ServerController;
import javax.swing.*;
import java.awt.*;

public class LPanel extends JPanel {
    private ServerController controller;
    private JTextField fromDate;
    private JTextField toDate;
    private JLabel fromDateText;
    private JLabel toDateText;
    private JLabel serverStatus;
    private JButton seeLog;


    public LPanel(ServerController controller, int width, int hight){
        this.controller = controller;
        this.setSize(width, hight);
        this.setLayout(null);
        this.setBackground(Color.GRAY);
        setUp();
    }

    private void setUp(){

        serverStatus = new JLabel("Server status: Online");
        serverStatus.setLocation(20, 100);
        serverStatus.setSize(150, 30);
        add(serverStatus);

        fromDate = new JTextField();
        fromDate.setSize(200, 22);
        fromDate.setLocation(100,255);
        add(fromDate);

        fromDateText = new JLabel("Från datum: ");
        fromDateText.setLocation(20, 250);
        fromDateText.setSize(100, 30);
        add(fromDateText);

        toDate = new JTextField();
        toDate.setSize(200, 22);
        toDate.setLocation(100, 305);
        add(toDate);

        toDateText = new JLabel("Till datum: ");
        toDateText.setLocation(20, 300);
        toDateText.setSize(100, 30);
        add(toDateText);

        seeLog = new JButton("OK");
        seeLog.setSize(75, 30);
        seeLog.setLocation(150, 370);
        add(seeLog);
    }
}
