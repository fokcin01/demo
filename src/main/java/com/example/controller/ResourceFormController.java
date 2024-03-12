package com.example.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ResourceFormController {
    public static void showEditWindow(JTable table, int row) {
        JFrame editFrame = new JFrame("Edit resource");

        editFrame.setLayout(new BorderLayout());

        JPanel panelWithLabelsAndFields = new JPanel();
        panelWithLabelsAndFields.setLayout(new GridBagLayout());
        JPanel panelWithButtons = new JPanel();

        editFrame.add(panelWithLabelsAndFields, BorderLayout.CENTER);
        editFrame.add(panelWithButtons, BorderLayout.SOUTH);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> editFrame.dispose());

        panelWithButtons.add(saveButton);
        panelWithButtons.add(cancelButton);

        int id = (int) table.getValueAt(row, 0);
        String name = (String) table.getValueAt(row, 1);
        int price = (int) table.getValueAt(row, 2);


        JTextField idField = new JTextField(String.valueOf(id));
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

        editFrame.setLocation(860,500);
        editFrame.setSize(800,400);

        saveButton.addActionListener(e -> {
            table.setValueAt(Integer.parseInt(idField.getText()), row, 0);
            table.setValueAt(nameField.getText(), row, 1);
            table.setValueAt(Integer.parseInt(priceField.getText()), row, 2);
            editFrame.dispose();
        });
        editFrame.setVisible(true);
    }

    private static GridBagConstraints createConstraint(int column, int row) {
        return new GridBagConstraints(column, row, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
    }
}
