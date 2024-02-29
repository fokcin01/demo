package com.example.demo;

import client.to.ResourceTO;
import com.example.http.HttpHandler;
import com.example.http.uri.Requests;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.util.List;


public class Controller {
    JPanel main;

    public JPanel getMain() {
        return main;
    }

    public Controller() {
        main = new JPanel();
        main.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);

        List<ResourceTO> resourceTOS = new HttpHandler<List<ResourceTO>>().sendRequest(Requests.RESOURCES_ALL, null);
        System.out.println("tos: " + resourceTOS);

        initTable(resourceTOS);
    }

    public void initTable(List<ResourceTO> resourceTOS) {
        JTable table = new JTable();
        table.setModel(new DefaultTableModel(new Object[][]{}, new String[] {"id", "name", "price"}));
        for (ResourceTO to : resourceTOS) {
            ((DefaultTableModel)table.getModel()).addRow(new Object[] {
                    to.getId(), to.getName(), to.getPrice()
            });
        }
        table.setTableHeader(new JTableHeader());
        main.add(table);
        main.setVisible(true);
    }

}