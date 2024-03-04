package com.example.demo;

import com.example.config.PropertiesHandler;

import javax.swing.*;

public class Application implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Application());
    }

    @Override
    public void run() {
        PropertiesHandler.init();
        JFrame frame = new JFrame("hueta ebanaya");
//        JFrame frame = new Controller().getMain();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocation(500,300);
//        JButton button = new JButton("polnyi pizdec");
        frame.getContentPane().add(new ResourceController().getMain());
//        frame.getContentPane().add(new NavBar().getMenu());

        frame.setVisible(true);
    }

}