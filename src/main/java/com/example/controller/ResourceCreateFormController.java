package com.example.controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ResourceCreateFormController extends JFrame {
    public static void showCreateWindow(JTable table, int row) {
        JFrame createFrame = new JFrame("create new resource");
        createFrame.setLayout(new BorderLayout());

        JPanel panelWithLabelsAndFields = new JPanel();
        panelWithLabelsAndFields.setLayout(new GridBagLayout());
        JPanel panelWithButtons = new JPanel();

        createFrame.add(panelWithLabelsAndFields, BorderLayout.CENTER);
        createFrame.add(panelWithButtons, BorderLayout.SOUTH);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> createFrame.dispose());

        panelWithButtons.add(saveButton);
        panelWithButtons.add(cancelButton);

        Integer id = null;
        String name = "nothing here";
        int price = 0;


        JTextField idField = new JTextField(Integer.valueOf(id));
        idField.setEditable(false);
        JTextField nameField = new JTextField(name);
        JTextField priceField = new JTextField(String.valueOf(price));
        JLabel idLabel = new JLabel("id");
        JLabel nameLabel = new JLabel("name");
        JLabel priceLabel = new JLabel("price");

        panelWithLabelsAndFields.add(idLabel, createConstraint(0, 0));
        panelWithLabelsAndFields.add(idField, createConstraint(1, 0));
        panelWithLabelsAndFields.add(nameLabel, createConstraint(0, 1));
        panelWithLabelsAndFields.add(nameField, createConstraint(1, 1));
        panelWithLabelsAndFields.add(priceLabel, createConstraint(0, 2));
        panelWithLabelsAndFields.add(priceField, createConstraint(1, 2));

        saveButton.addActionListener(e -> {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.addRow(new Object[]{id, name, price});

            table.setValueAt(Integer.parseInt(idField.getText()), row, 0);
            table.setValueAt(nameField.getText(), row, 1);
            table.setValueAt(Integer.parseInt(priceField.getText()), row, 2);
            createFrame.dispose();
        });

        createFrame.setLocation(860, 500);
        createFrame.setSize(800, 400);
        createFrame.setVisible(true);


    }

    private static GridBagConstraints createConstraint(int column, int row) {
        return new GridBagConstraints(column, row, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
    }

}

