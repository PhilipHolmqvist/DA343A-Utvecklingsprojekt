package client.view;

import client.controller.ClientController;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class LoginWindow extends JDialog {
    private JLabel labelUsername;
    private JLabel labelServerName;
    private JLabel labelPort;
    private JTextField username;
    private JTextField serverName;
    private JTextField port;
    private JButton btnLogin;
    private JButton btnCancel;
    private JButton btnChooseIcon;
    private boolean authenticated;
    private String imagePath = "avatars/blue.png";

    public boolean authenticate() {
        return authenticated;
    }

    public LoginWindow(Frame parent) {
        super(parent, "Login", true);
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;

        labelUsername = new JLabel("Användarnamn: ");
        cs.gridx = 0;
        cs.gridy = 0;
        cs.gridwidth = 1;
        panel.add(labelUsername, cs);

        username = new JTextField(20);
        cs.gridx = 1;
        cs.gridy = 0;
        cs.gridwidth = 2;
        panel.add(username, cs);

        labelServerName = new JLabel("Server: ");
        cs.gridx = 0;
        cs.gridy = 1;
        cs.gridwidth = 1;
        panel.add(labelServerName, cs);

        serverName = new JTextField(20);
        serverName.setText("127.0.0.1");
        cs.gridx = 1;
        cs.gridy = 1;
        cs.gridwidth = 2;
        panel.add(serverName, cs);

        labelPort = new JLabel("Port: ");
        cs.gridx = 0;
        cs.gridy = 2;
        cs.gridwidth = 1;
        panel.add(labelPort, cs);

        port = new JTextField(20);
        port.setText("721");
        cs.gridx = 1;
        cs.gridy = 2;
        cs.gridwidth = 2;
        panel.add(port, cs);

        btnLogin = new JButton("Logga in");
        btnLogin.addActionListener(e -> {
            authenticated = true;
            dispose();
        });

        btnCancel = new JButton("Avbryt");
        btnCancel.addActionListener( e -> {
            authenticated = false;
            dispose();
        });

        btnChooseIcon = new JButton("Välj profilbild");
        btnChooseIcon.addActionListener( e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(false);
            fileChooser.setCurrentDirectory(new java.io.File("./avatars"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg & png", "png", "jpg");
            fileChooser.setFileFilter(filter);

            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                imagePath = fileChooser.getSelectedFile().getAbsolutePath();
            }
        });

        JPanel bp = new JPanel();
        bp.add(btnLogin);
        bp.add(btnCancel);
        bp.add(btnChooseIcon);

        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(bp, BorderLayout.PAGE_END);

        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public String getUsername() {
        return username.getText();
    }

    public String getServerName() {
        return serverName.getText();
    }

    public int getPort() {
        return Integer.parseInt(port.getText());
    }

    public String getImagePath() {
        return imagePath;
    }

    }




