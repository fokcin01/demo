package com.example.demo;

import javax.swing.*;

public class NavBar {
    JMenu menu;

    public JMenu getMenu() {
        return menu;
    }

    void MenuExample(JFrame frame) {

        JMenuBar mb = new JMenuBar();
        menu = new JMenu("Menu");


        frame.setJMenuBar(mb);
        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
