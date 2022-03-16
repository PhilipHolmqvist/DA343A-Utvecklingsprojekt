package server.view;

import server.controller.ServerController;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LPanel extends JPanel {
    private ServerController controller;
    private JTextField fromDate;
    private JTextField toDate;
    private JLabel fromDateText;
    private JLabel toDateText;
    private JLabel serverStatus;
    private JLabel inputText;
    private JButton seeLog;

    public LPanel(ServerController controller, int width, int hight){
        this.controller = controller;
        this.setSize(width, hight);
        this.setLayout(null);
        setUp();
    }

    private void setUp(){

        serverStatus = new JLabel("Server status: Online");
        serverStatus.setLocation(20, 100);
        serverStatus.setSize(150, 30);
        add(serverStatus);

        inputText = new JLabel("yyyymmdd-tttt");
        inputText.setForeground(Color.gray);
        inputText.setSize(100,20);
        inputText.setLocation(130, 230);
        add(inputText);

        fromDate = new JTextField();
        fromDate.setSize(200, 22);
        fromDate.setLocation(125,255);
        fromDate.setBorder(new BevelBorder(BevelBorder.LOWERED));
        add(fromDate);

        fromDateText = new JLabel("Från datum: ");
        fromDateText.setLocation(48, 250);
        fromDateText.setSize(100, 30);
        add(fromDateText);

        toDate = new JTextField();
        toDate.setSize(200, 22);
        toDate.setLocation(125, 305);
        toDate.setBorder(new BevelBorder(BevelBorder.LOWERED));
        add(toDate);

        toDateText = new JLabel("Till datum: ");
        toDateText.setLocation(57, 300);
        toDateText.setSize(100, 30);
        add(toDateText);

        seeLog = new JButton("OK");
        seeLog.setSize(80, 30);
        seeLog.setLocation(185, 350);
        add(seeLog);
        ActionListener listener = new ButtonActionListeners();
        seeLog.addActionListener(listener);


    }


    class ButtonActionListeners implements ActionListener {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == seeLog) {
                controller.displayLog(fromDate.getText(), toDate.getText());
            }
        }
    }
}
