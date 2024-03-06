package com.example.controller;

import javax.swing.*;

public interface SwingController {
    JPanel getControllerPanel();
    void fillData();
    void configuration();
    default void init() {
        fillData();
        configuration();
    }
}
