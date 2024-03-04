package com.example.demo;

import client.to.ResourceTO;
import com.example.http.HttpHandler;
import com.example.http.uri.Requests;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

import static com.example.demo.EditWindow.showEditWindow;


public class ResourceController {
    JPanel main;

    public JPanel getMain() {
        return main;
    }

    public ResourceController() {
        main = new JPanel();
        main.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        main.setLayout(new BorderLayout());
        List<ResourceTO> resourceTOS = new HttpHandler<List<ResourceTO>>().sendRequest(Requests.RESOURCES_ALL, null);
        System.out.println("tos: " + resourceTOS);
        initTable(resourceTOS);
    }

    public void initTable(List<ResourceTO> resourceTOS) {
        JTable table = new JTable();
        table.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"id", "name", "price"}));
        for (ResourceTO to : resourceTOS) {
            ((DefaultTableModel) table.getModel()).addRow(new Object[]{
                    to.getId(), to.getName(), to.getPrice()
            });
        }
        table.setRowHeight(30);
        table.setTableHeader(new JTableHeader());
        main.add(table);
        main.setVisible(true);

        table.setEnabled(false);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
//                int row = table.rowAtPoint(e.getPoint());
//                int column = table.columnAtPoint(e.getPoint());
                if (e.getClickCount() == 2) {
                    int row = table.rowAtPoint(e.getPoint());
                    showEditWindow(table, row);

                }
            }
        });
        table.setBorder(new LineBorder(Color.black));
    }
}