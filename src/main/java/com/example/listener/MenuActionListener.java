package com.example.listener;

import com.example.controller.SwingController;
import com.example.controller.chat.ChatController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CompletableFuture;

public class MenuActionListener implements ActionListener {
    private static final Logger logger = LoggerFactory.getLogger(MenuActionListener.class);
    JFrame mainFrame;
    SwingController controller;

    public MenuActionListener(JFrame mainFrame, SwingController controller) {
        logger.info("constructor");
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
