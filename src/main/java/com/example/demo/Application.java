package com.example.demo;

import com.example.config.PropertiesHandler;

import javax.swing.*;
import java.awt.*;

public class Application implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Application());
    }

    @Override
    public void run() {
        PropertiesHandler.init();
        NavBar navBar = new NavBar();
        JFrame frame = new JFrame("hueta ebanaya");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocation(500,300);
        frame.getContentPane().add(new ResourceController().getMain(), BorderLayout.CENTER);
        frame.setJMenuBar(navBar.getMb());

        frame.setVisible(true);
    }

}