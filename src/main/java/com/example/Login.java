package com.example;

import client.to.Constants;
import client.to.ResourceTO;
import client.to.UserTO;
import com.example.Services.ImageReader;
import com.example.config.PropertiesHandler;
import com.example.controller.UsersController;
import com.example.http.HttpHandler;
import com.example.http.uri.Requests;
import com.example.ui.CustomPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static com.example.controller.ResourceFormController.createConstraint;

public class Login implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Login());
    }

    @Override
    public void run() {
        JFrame mainFrame = new JFrame("Authorization");
        ImageReader imReader = new ImageReader();
        PropertiesHandler.init();
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(400, 250);
        mainFrame.setResizable(false);          // эта хуйня запрещает менять размер
        mainFrame.setLocationRelativeTo(null);  // эта хуйня центрует
        mainFrame.setVisible(true);

        JPanel panelWithLabelsAndFields = new CustomPanel();
        panelWithLabelsAndFields.setLayout(new GridBagLayout());
        mainFrame.add(panelWithLabelsAndFields, BorderLayout.CENTER);

        int top = 10, left = 10, bottom = 10, right = 10;
        panelWithLabelsAndFields.setBorder(new EmptyBorder(top, left, bottom, right));

        JTextField loginField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JLabel loginLabel = new JLabel("login");
        JLabel passLabel = new JLabel("password");

        panelWithLabelsAndFields.add(loginLabel, createConstraint(0, 1));
        panelWithLabelsAndFields.add(loginField, createConstraint(1, 1));
        panelWithLabelsAndFields.add(passLabel, createConstraint(0, 2));
        panelWithLabelsAndFields.add(passField, createConstraint(1, 2));

        JPanel panelWithButtons = new CustomPanel();
        panelWithButtons.setLayout(new GridBagLayout());
        mainFrame.add(panelWithButtons, BorderLayout.SOUTH);

        JButton enterButton = new JButton("enter");
        enterButton.setIcon(imReader.createImageIcon("enter.png", 16, 16));
        enterButton.addActionListener(e -> {
            String login = loginField.getText();
            String password = passField.getText();

            if (login.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "Login or password are required", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                UserTO user = new UserTO();
                user.setUsername(loginField.getText());
                user.setUserPassword(passField.getText());
                login(user);
                mainFrame.dispose();
            }
        });

        JButton cancelButton = new JButton("cancel");
        cancelButton.setIcon(imReader.createImageIcon("cancel.png", 16, 16));
        cancelButton.addActionListener(e -> mainFrame.dispose());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelWithButtons.add(enterButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panelWithButtons.add(cancelButton, gbc);

        JLabel reglink = createRegistrationLink(mainFrame);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panelWithButtons.add(reglink, gbc);

        JLabel forgotPasswordLink = createForgotPasswordLink(mainFrame);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelWithButtons.add(forgotPasswordLink, gbc);
    }

    public JLabel createRegistrationLink(JFrame currentFrame) {
        JLabel reglink = new JLabel("<html><a href=''>Has no account? Sign up</a></html>");
        reglink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        reglink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentFrame.dispose();
                try {
                    UsersController usersController = new UsersController();
                    usersController.Registration();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        return reglink;
    }

    private JLabel createForgotPasswordLink(JFrame currentFrame) {
        JLabel forgotPasswordLink = new JLabel("<html><a href=''>Forget a password?</a></html>");
        forgotPasswordLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotPasswordLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentFrame.dispose();
            }
        });
        return forgotPasswordLink;
    }

    public static void login(UserTO user) {
        System.out.println("usersLogin: " + user.getUsername() + " in -> login(userByLogin)");
        String answer = new HttpHandler<String>().sendRequest(Requests.USERS_LOGIN, user);
        if (answer.equals(Constants.LOGIN_OK)) {
            System.out.println(Constants.LOGIN_OK);
            Runnable app = new Application();
            app.run();
        } else {
            System.out.println(Constants.LOGIN_FAILED);
        }
    }
}