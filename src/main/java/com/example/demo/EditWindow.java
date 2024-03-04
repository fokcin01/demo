package com.example.demo;

import javax.swing.*;
import java.awt.*;

public class EditWindow {
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
}
