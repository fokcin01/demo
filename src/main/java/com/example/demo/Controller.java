package com.example.demo;

import client.to.ResourceTO;
import com.example.http.HttpHandler;
import com.example.http.uri.Requests;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.List;


public class Controller {
    JPanel main;

    public JPanel getMain() {
        return main;
    }

    public Controller() {
        main = new JPanel();
        main.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        main.setLayout(new BorderLayout());
        List<ResourceTO> resourceTOS = new HttpHandler<List<ResourceTO>>().sendRequest(Requests.RESOURCES_ALL, null);
        System.out.println("tos: " + resourceTOS);
        initTable(resourceTOS);
    }

    public static void showEditWindow(JTable table, int row) {
        JFrame editFrame = new JFrame("Edit Window");
        JPanel editPanel = new JPanel(new GridLayout(0, 2));


        int id = (int) table.getValueAt(row, 0);
        String name = (String) table.getValueAt(row, 1);
        int price = (int) table.getValueAt(row, 2);


        JTextField idField = new JTextField(String.valueOf(id));
        JTextField nameField = new JTextField(name);
        JTextField priceField = new JTextField(String.valueOf(price));

        editFrame.setLocation(860,500);
        editFrame.setSize(800,400);

        editPanel.add(new JLabel("ID:"));
        editPanel.add(idField);
        editPanel.add(new JLabel("Name:"));
        editPanel.add(nameField);
        editPanel.add(new JLabel("Price:"));
        editPanel.add(priceField);


        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {

            table.setValueAt(Integer.parseInt(idField.getText()), row, 0);
            table.setValueAt(nameField.getText(), row, 1);
            table.setValueAt(Integer.parseInt(priceField.getText()), row, 2);
            editFrame.dispose();
        });
        editPanel.add(saveButton);
        editFrame.getContentPane().add(editPanel);
        editFrame.pack();
        editFrame.setVisible(true);
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