package com.example.controller;

import client.to.ResourceTO;
import com.example.http.HttpHandler;
import com.example.http.uri.Requests;
import com.example.ui.CustomPanel;

import javax.swing.*;
import java.awt.*;

public class ResourceFormController extends JFrame {

    public static void saveItem(ResourceTO res) {
        System.out.println("saved item with id: " + res.getId() + " in ->ResFormCont(saveItem)");
        new HttpHandler<>().sendRequest(Requests.RESOURCES_SAVE, res);

    }

    public static void showEditWindow(JTable table, Integer row,ResourceController resourceController) {
        JFrame editFrame = new JFrame("Edit resource");
        editFrame.setLayout(new BorderLayout());

        editFrame.setSize(800, 400);
        editFrame.setLocationRelativeTo(null);  // эта хуйня центрует
        editFrame.setVisible(true);

        JPanel panelWithLabelsAndFields = new CustomPanel();
        panelWithLabelsAndFields.setLayout(new GridBagLayout());
        JPanel panelWithButtons = new CustomPanel();

        editFrame.add(panelWithLabelsAndFields, BorderLayout.CENTER);
        editFrame.add(panelWithButtons, BorderLayout.SOUTH);

        JButton saveButton = new JButton("save");
        JButton cancelButton = new JButton("cancel");
        cancelButton.addActionListener(e -> editFrame.dispose());

        panelWithButtons.add(saveButton);
        panelWithButtons.add(cancelButton);

        Integer id;
        String name;
        int price;
        if (row == null || row == -1) {
            id = null;
            name = "Новый ресурс";
            price = 0;
        } else {
            id = (int) table.getValueAt(row, 0);
            name = (String) table.getValueAt(row, 1);
            price = (int) table.getValueAt(row, 2);
        }

        JTextField idField = new JTextField(id == null ? "": id.toString() );
        idField.setEditable(false);

        JLabel idLabel = new JLabel("id");
        JLabel nameLabel = new JLabel("name");
        JLabel priceLabel = new JLabel("price");

        System.out.println("id = " + id);
        if (row == -1) {
            System.out.println("id == null");
            editFrame.setTitle("Create resource");

            JTextField nameField = new JTextField();
            JTextField priceField = new JTextField();

            panelWithLabelsAndFields.add(nameLabel, createConstraint(0, 1));
            panelWithLabelsAndFields.add(nameField, createConstraint(1, 1));
            panelWithLabelsAndFields.add(priceLabel, createConstraint(0, 2));
            panelWithLabelsAndFields.add(priceField, createConstraint(1, 2));

            saveButton.addActionListener(e -> {
                ResourceTO resource = new ResourceTO();
                resource.setName(nameField.getText());
                resource.setPrice(Integer.parseInt(priceField.getText()));
                saveItem(resource);
                editFrame.dispose();
                resourceController.updateTable();
            });
        } else {
            System.out.println("id != null");
            JTextField nameField = new JTextField(name);
            JTextField priceField = new JTextField(String.valueOf(price));

            panelWithLabelsAndFields.add(idLabel, createConstraint(0, 0));
            panelWithLabelsAndFields.add(idField, createConstraint(1, 0));
            panelWithLabelsAndFields.add(nameLabel, createConstraint(0, 1));
            panelWithLabelsAndFields.add(nameField, createConstraint(1, 1));
            panelWithLabelsAndFields.add(priceLabel, createConstraint(0, 2));
            panelWithLabelsAndFields.add(priceField, createConstraint(1, 2));

            saveButton.addActionListener(e -> {
                ResourceTO resource = new ResourceTO();
                resource.setId(id);
                resource.setName(nameField.getText());
                resource.setPrice(Integer.parseInt(priceField.getText()));
                saveItem(resource);
                editFrame.dispose();
                resourceController.updateTable();
            });
        }

    }

    public static GridBagConstraints createConstraint(int column, int row) {
        return new GridBagConstraints(column, row, 1, 1, 1.0, 1.0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0);
    }
}
