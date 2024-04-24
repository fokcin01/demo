package com.example.controller;

import client.to.ResourceTO;
import client.to.UserTO;
import com.example.http.HttpHandler;
import com.example.http.uri.Requests;
import com.example.ui.CustomPanel;
import com.example.ui.CustomTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.controller.ResourceFormController.showEditWindow;


public class ResourceController implements SwingController {
    JPanel main;
    JTable table = new CustomTable();
    private List<ResourceTO> resourceTOS = new ArrayList<>();
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
        resourceTOS = new HttpHandler<List<ResourceTO>>().sendRequest(Requests.RESOURCES_ALL, null);
        usersTOS = new HttpHandler<List<UserTO>>().sendRequest(Requests.USERS_ALL, null);
        System.out.println("users: " + usersTOS);
        System.out.println("users array: " + Arrays.toString(usersTOS.toArray()));
    }

    @Override
    public void configuration() {
        this.main = new CustomPanel();
        main.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        main.setLayout(new BorderLayout());
        updateTable();
    }

    public void updateTable() {
        SwingUtilities.invokeLater(() -> {
            List<ResourceTO> resources = new HttpHandler<List<ResourceTO>>().sendRequest(Requests.RESOURCES_ALL, null);
            resourceTOS.clear();
            resourceTOS.addAll(resources);
            initTable(resourceTOS);
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


        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (e.getClickCount() == 2) {
                    int row = table.rowAtPoint(e.getPoint());
                    showEditWindow(table, row);
                }
            }
        });
//        table.setBorder(new LineBorder(Color.black));

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem popupItem1 = new JMenuItem("edit resource");
        popupItem1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("get point: " + e.getPoint());
                showEditWindow(table, table.getSelectedRow());
                updateTable();
            }
        });
        JMenuItem popupItem2 = new JMenuItem("delete resource");
        popupItem2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                createDialog(table.getSelectedRow());
            }
        });
        JMenuItem popupItem3 = new JMenuItem("create new resource");
        popupItem3.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showEditWindow(table, 0);
                updateTable();
            }
        });

        popupMenu.add(popupItem1);
        popupMenu.add(popupItem2);
        popupMenu.add(popupItem3);
        table.setComponentPopupMenu(popupMenu);
    }

    private void createDialog(int rowNum) {
        int result = JOptionPane.showConfirmDialog(getControllerPanel(), "pohui");
        System.out.println("result: " + result);
        if (result == 0) {
            System.out.println(rowNum + " rowNum");
            Integer idColumnValue = (Integer) table.getValueAt(rowNum, 0);
            ResourceTO res = new ResourceTO();
            res.setId(idColumnValue);
            deleteItem(res);
        } else {
            System.out.println("nothing");
        }
    }

    private void deleteItem(ResourceTO res) {
        System.out.println("delete item with id: " + res.getId());
        new HttpHandler<>().sendRequest(Requests.RESOURCES_DELETE, res);
        updateTable();
    }
}