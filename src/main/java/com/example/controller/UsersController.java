package com.example.controller;

import javax.swing.*;

public class UsersController implements SwingController {
    JPanel panel;

    public UsersController() {
        init();
    }

    @Override
    public JPanel getControllerPanel() {
        return panel;
    }

    @Override
    public void fillData() {
    }

    @Override
    public void configuration() {
        this.panel = new JPanel();
        panel.add(new JLabel("users"));
        panel.setVisible(true);

    }

}
