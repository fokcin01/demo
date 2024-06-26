package com.example.controller;

import javax.swing.*;

public interface SwingController {
    JPanel getControllerPanel();
    void fillData();
    void configuration();

    /**
     * метод надо засовывать в конструктор контроллера,
     * либо вызывать нужные методы в конструкторе
     */
    default void init() {
        fillData();
        configuration();
    }
}
