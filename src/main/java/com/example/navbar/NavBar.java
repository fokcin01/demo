package com.example.navbar;

import com.example.controller.chat.ChatController;
import com.example.controller.ResourceController;
import com.example.controller.UsersController;
import com.example.listener.MenuActionListener;
import com.example.ui.CustomMenuItem;

import javax.swing.*;

public class NavBar {
    JMenuBar mb;

    public JMenuBar getMb() {
        return mb;
    }

    public NavBar(JFrame mainFrame) {
        mb = new JMenuBar();
        mb.setBorderPainted(true);
        JMenuItem chat = new CustomMenuItem("Chat");
        JMenuItem resourcesAll = new CustomMenuItem("All resources");
        JMenuItem users = new CustomMenuItem("Users");

        resourcesAll.addActionListener(new MenuActionListener(mainFrame, new ResourceController()));
        chat.addActionListener(new MenuActionListener(mainFrame, new ChatController()));
        users.addActionListener(new MenuActionListener(mainFrame, new UsersController()));

        mb.add(chat);
        mb.add(users);
        mb.add(resourcesAll);
    }

}
