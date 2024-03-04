package com.example.demo;

import javax.swing.*;
import java.awt.*;

public class NavBar {
    JMenuBar mb;

    public JMenuBar getMb() {
        return mb;
    }

    public NavBar() {
        mb = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menu.setBackground(Color.DARK_GRAY);
        menu.setSize(Integer.MAX_VALUE, 50);
        mb.add(menu);
    }
}
