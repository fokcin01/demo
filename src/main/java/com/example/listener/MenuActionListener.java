package com.example.listener;

import com.example.controller.SwingController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;

public class MenuActionListener implements ActionListener {
    JFrame mainFrame;
    SwingController controller;

    public MenuActionListener(JFrame mainFrame, SwingController controller) {
        System.out.println("constructor");
        this.mainFrame = mainFrame;
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        CompletableFuture.runAsync(() -> mainFrame.getContentPane().removeAll())
                .thenRun(() -> {
                    mainFrame.getContentPane().add(controller.getControllerPanel());
                    mainFrame.setVisible(true);
                    mainFrame.repaint();
                });


    }
}
