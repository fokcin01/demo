package com.example.controller;

import client.to.UserTO;
import com.example.http.HttpHandler;
import com.example.http.uri.Requests;
import com.example.ui.CustomPanel;
import com.example.ui.CustomTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UsersController implements SwingController {
    JPanel panel;
    private List<UserTO> userTOS;

    public UsersController() {
        init();
    }

    @Override
    public JPanel getControllerPanel() {
        return panel;
    }

    @Override
    public void fillData() {
        userTOS = new HttpHandler<List<UserTO>>().sendRequest(Requests.USERS_ALL, null);
        System.out.println("tos: " + userTOS);
    }

    public void initTable(List<UserTO> userTOS) {
        JTable usersTable = new CustomTable();
        panel.add(usersTable, BorderLayout.CENTER);
        usersTable.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"id", "user"}));
        for (UserTO to: userTOS) {
            ((DefaultTableModel)usersTable.getModel()).addRow(new Object[] {
                    to.getId(), to.getUsername()
            });
        }
        panel.add(usersTable.getTableHeader(), BorderLayout.NORTH);
        panel.add(usersTable, BorderLayout.CENTER);
    }

    @Override
    public void configuration() {
        this.panel = new CustomPanel();
        panel.setLayout(new BorderLayout());
        initTable(userTOS);
    }

}
