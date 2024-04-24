package com.example;

import client.to.Constants;
import client.to.ResourceTO;
import client.to.UserTO;
import com.example.config.PropertiesHandler;
import com.example.http.HttpHandler;
import com.example.http.uri.Requests;
import com.example.ui.CustomPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Login implements Runnable{
    public static void main(String[] args) {SwingUtilities.invokeLater(new Login());}
    // СДЕЛАТЬ ПРОСТО СВИНГ МОМЕНТ (НЕ РАБОТАЮЩАЯ АВТОРИЗАЦИЯ)
    @Override
    public void run() {
        JFrame mainFrame = new JFrame("Authorization");
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

        mainFrame.add(panelWithLabelsAndFields, BorderLayout.CENTER);

        JTextField loginField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JLabel loginLabel = new JLabel("login");
        JLabel passLabel = new JLabel("password");

        panelWithLabelsAndFields.add(loginLabel, createConstraint(0, 1));
        panelWithLabelsAndFields.add(loginField, createConstraint(1, 1));
        panelWithLabelsAndFields.add(passLabel, createConstraint(0, 2));
        panelWithLabelsAndFields.add(passField, createConstraint(1, 2));

        JButton enterButton = new JButton("enter");
        enterButton.setIcon(createImageIcon("enter.png", 16, 16));
        enterButton.addActionListener(e -> {
            String login = loginField.getText();
            String password = passField.getText();

            if (login.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(mainFrame, "Login or password are required", "Go fuck yourself", JOptionPane.ERROR_MESSAGE);
            } else {
                UserTO user = new UserTO();
                user.setUsername(loginField.getText());
                user.setUserPassword(passField.getText());
                login(user);
                mainFrame.dispose();
            }


        });
        JButton cancelButton = new JButton("cancel");
        cancelButton.setIcon(createImageIcon("cancel.png", 16, 16));
        cancelButton.addActionListener(e -> mainFrame.dispose());

        JPanel panelWithButtons = new CustomPanel();
        panelWithButtons.add(enterButton);
        panelWithButtons.add(cancelButton);
        mainFrame.add(panelWithButtons, BorderLayout.SOUTH);
    }
    private ImageIcon createImageIcon(String fileName, int width, int height) {
        InputStream stream = getClass().getResourceAsStream("/" + fileName);
        if (stream != null) {
            try {
                Image img = ImageIO.read(stream).getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(img);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Couldn't find file: " + fileName);
        }
        return null;
    }
    private static GridBagConstraints createConstraint(int column, int row) {
        return new GridBagConstraints(column, row, 1, 1, 1.0, 1.0,
                GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL,
                new Insets(0, 0, 0, 0), 0, 0);
    }
    public static void login(UserTO user) {
        System.out.println("usersLogin: " + user.getUsername()+ " in -> login(userByLogin)");
        String answer = new HttpHandler<String>().sendRequest(Requests.USERS_LOGIN, user);
        if(answer.equals(Constants.LOGIN_OK)){
            System.out.println(Constants.LOGIN_OK);
            Runnable app = new Application();
            app.run();
        }else{
            System.out.println(Constants.LOGIN_FAILED);
        }
    }
}
