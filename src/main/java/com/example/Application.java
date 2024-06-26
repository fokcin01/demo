package com.example;

import com.example.config.PropertiesHandler;
import com.example.navbar.NavBar;

import javax.swing.*;
import java.awt.*;

public class Application implements Runnable {
    /**
     * id залогиненного юзера, которое хранится на клиенте
     */
    private static Integer loggedUserId;

    protected static void setLoggedUserId(Integer userId) {
        loggedUserId = userId;
    }

    public static Integer getLoggedUserId() {
        return loggedUserId;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Application());
    }

    @Override
    public void run() {
        if (getLoggedUserId() == null) {
            throw new RuntimeException("logged user id in answer is null, залогинься как человек бля");
        }
        JFrame mainFrame = new JFrame("hueta ebanaya");
        PropertiesHandler.init();
        NavBar navBar = new NavBar(mainFrame);

        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(900, 600);
        mainFrame.setLocation(500,300);
        mainFrame.setJMenuBar(navBar.getMb());

        mainFrame.setVisible(true);
    }

}