package com.example.controller;

import client.to.ResourceTO;
import client.to.UserTO;
import com.example.http.HttpHandler;
import com.example.http.uri.Requests;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static com.example.controller.ResourceFormController.showEditWindow;


public class ResourceController implements SwingController {
    JPanel main;
    JTable table = new JTable();
    private final List<ResourceTO> resourceTOS = new ArrayList<>();
    private List<UserTO> usersTOS = new ArrayList<>();

    public ResourceController() {
        init();
    }

    @Override
    public JPanel getControllerPanel() {
        return main;
    }

    @Override
    public void fillData() {
//        resourceTOS = new HttpHandler<List<ResourceTO>>().sendRequest(Requests.RESOURCES_ALL, null);
        usersTOS = new HttpHandler<List<UserTO>>().sendRequest(Requests.USERS_ALL, null);
        System.out.println("users: " + usersTOS);
        System.out.println("users array: " + Arrays.toString(usersTOS.toArray()));
    }

    @Override
    public void configuration() {
        this.main = new JPanel();
        main.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        main.setLayout(new BorderLayout());
        JComboBox<UserTO> comboBox = new JComboBox<>();
        usersTOS.forEach(item -> {
            System.out.println("item: " + item);
            comboBox.addItem(item);
        });

        main.add(comboBox, BorderLayout.AFTER_LAST_LINE);
        comboBox.addActionListener(e -> {
            System.out.println("change item to: " + comboBox.getModel().getSelectedItem());
            SwingUtilities.invokeLater(() -> {
                new HttpHandler<>().sendRequest(Requests.USERS_LOGIN, (UserTO)comboBox.getModel().getSelectedItem());
                List<ResourceTO> resources = new HttpHandler<List<ResourceTO>>().sendRequest(Requests.RESOURCES_ALL, null);
                resourceTOS.clear();
                resourceTOS.addAll(resources);
                initTable(resourceTOS);
            });
        });
    }

    public void initTable(List<ResourceTO> resourceTOS) {

        table.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"id", "name", "price"}));
        for (ResourceTO to : resourceTOS) {
            ((DefaultTableModel) table.getModel()).addRow(new Object[]{
                    to.getId(), to.getName(), to.getPrice()
            });
        }
        table.setRowHeight(30);
        main.add(table.getTableHeader(), BorderLayout.NORTH);
        main.add(table, BorderLayout.CENTER);
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

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem popupItem1 = new JMenuItem("edit resource");
        popupItem1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
//                super.mousePressed(e);
                showEditWindow(table, table.rowAtPoint(e.getPoint()));
            }
        });
        JMenuItem popupItem2 = new JMenuItem("delete resource");
        popupItem2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
//                super.mousePressed(e);
                int i = table.rowAtPoint(e.getPoint());
                table.getSelectionModel().setSelectionInterval(i,i);
                createDialog(i);
            }
        });
        popupMenu.add(popupItem1);
        popupMenu.add(popupItem2);
        table.setComponentPopupMenu(popupMenu);

    }
    private void createDialog(int rowNum) {

//        final JDialog modelDialog = new JDialog(frame, "Swing Tester",
//                Dialog.ModalityType.DOCUMENT_MODAL);
//        modelDialog.setBounds(132, 132, 300, 200);
//        Container dialogContainer = modelDialog.getContentPane();
//        dialogContainer.setLayout(new BorderLayout());
//        dialogContainer.add(new JLabel("                         Welcome to Swing!")
//                , BorderLayout.CENTER);
//        JPanel panel1 = new JPanel();
//        panel1.setLayout(new FlowLayout());
//        JButton okButton = new JButton("Ok");
//        okButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                modelDialog.setVisible(false);
//            }
//        });
//
//        panel1.add(okButton);
//        dialogContainer.add(panel1, BorderLayout.SOUTH);
//
//        return  modelDialog;
        int result = JOptionPane.showConfirmDialog(getControllerPanel(), "pohui");
        System.out.println(result);
        if (result == 0){
            System.out.println(rowNum +" rowNum");

            new HttpHandler<>().sendRequest(Requests.RESOURCES_DELETE, (Serializable) table.getModel().
                    getValueAt(rowNum,0));
        }else {
            
        }
    }

}