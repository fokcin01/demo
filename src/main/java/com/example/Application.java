package com.example;

import com.example.config.PropertiesHandler;
import com.example.navbar.NavBar;

import javax.swing.*;
import java.awt.*;

public class Application implements Runnable {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Application());
    }

    @Override
    public void run() {
        JFrame mainFrame = new JFrame("hueta ebanaya");
        PropertiesHandler.init();
        NavBar navBar = new NavBar(mainFrame);

        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(900, 600);
        mainFrame.setLocation(500,300);
//        mainFrame.getContentPane().add(new ResourceController().getControllerPanel(), BorderLayout.CENTER);
        mainFrame.setJMenuBar(navBar.getMb());

        mainFrame.setVisible(true);
    }

}