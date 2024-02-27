package com.example.demo;

import javax.swing.*;

public class Application implements Runnable {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Application());
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("hueta ebanaya");
//        JFrame frame = new Controller().getMain();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
//        JButton button = new JButton("polnyi pizdec");
        frame.getContentPane().add(new Controller().getMain());
        frame.setVisible(true);
    }
}