package com.example.navbar;

import com.example.controller.ResourceController;
import com.example.controller.UsersController;
import com.example.listener.MenuActionListener;

import javax.swing.*;

public class NavBar {
    JMenuBar mb;

    public JMenuBar getMb() {
        return mb;
    }

    public NavBar(JFrame mainFrame) {
        mb = new JMenuBar();
        JMenuItem menu = new JMenuItem("Resources");
        JMenuItem resourcesAll = new JMenuItem("All resources");
        JMenuItem users = new JMenuItem("Users");

        resourcesAll.addActionListener(new MenuActionListener(mainFrame, new ResourceController()));
//        menu.addActionListener(new MenuActionListener(mainFrame, null));
        users.addActionListener(new MenuActionListener(mainFrame, new UsersController()));

        mb.add(menu);
        mb.add(users);
        mb.add(resourcesAll);
    }

}
