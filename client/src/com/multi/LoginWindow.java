package com.multi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginWindow extends JFrame {
    private final ClientMain client;
    JTextField loginField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("Login");

    LoginWindow() {
        super("Login");

        this.client = new ClientMain("localhost", 8818);
        client.connect();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(loginField);
        panel.add(passwordField);
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLogin();
            }
        });

        getContentPane().add(panel, BorderLayout.CENTER);

        pack();

        setVisible(true);
    }

    private void doLogin() {
        String login = loginField.getText();
        String password = passwordField.getText();

        try {
            if (client.login(login, password)) {
                // bring up the user list window
                UserListPane userListPane = new UserListPane(client);
                JFrame frame = new JFrame("User List");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 600);


                frame.getContentPane().add(userListPane, BorderLayout.CENTER);
                frame.setVisible(true);

                setVisible(false);
            } else {
                // show error message
                JOptionPane.showMessageDialog(this, "Invalid access.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.setVisible(true);


    }
}
