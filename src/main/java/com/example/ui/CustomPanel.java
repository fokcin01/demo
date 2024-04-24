package com.example.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CustomPanel extends JPanel {
    public CustomPanel() {
        int top = 0, left = 2, bottom = 4, right = 2;
        this.setBorder(new EmptyBorder(top, left, bottom, right));
    }
}
