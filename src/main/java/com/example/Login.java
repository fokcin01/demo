package com.example;

import client.to.Constants;
import client.to.LoginAnswer;
import client.to.UserTO;
import com.example.http.Wrapper;
import com.example.http.WrapperDeserializer;
import com.example.services.ImageReader;
import com.example.config.PropertiesHandler;
import com.example.controller.UsersController;
import com.example.http.HttpHandler;
import com.example.http.uri.Requests;
import com.example.ui.CustomPanel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.Serializable;

import static com.example.controller.ResourceFormController.createConstraint;

public class Login implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Login.class);

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
        mainFrame.getRootPane().setDefaultButton(enterButton);
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
        logger.info("usersLogin: " + user.getUsername() + " in -> login(userByLogin)");
        String answer = new HttpHandler().sendRequestAndGetJson(Requests.USERS_LOGIN, user);
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Wrapper.class, new WrapperDeserializer());
        objectMapper.registerModule(module);
        LoginAnswer loginAnswer = null;
        try {
            loginAnswer = objectMapper.readValue(answer, LoginAnswer.class);
            if (loginAnswer.getAnswer().equals(Constants.LOGIN_OK)) {
                logger.info(Constants.LOGIN_OK);
                Application app = new Application();
                Application.setLoggedUserId(loginAnswer.getLoggerUserId());
                app.run();
            } else {
                logger.info(Constants.LOGIN_FAILED);
            }
        } catch (IOException e) {
            logger.error("exception at reading json", e);
            e.printStackTrace();
        }

    }
}