package com.example.ui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CustomMenuItem extends JMenuItem {
    public CustomMenuItem(String name) {
        super(name);
        setBorder(new LineBorder(Color.black));
    }
}
