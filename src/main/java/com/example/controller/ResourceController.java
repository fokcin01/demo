package com.example.controller;

import client.to.ResourceTO;
import client.to.UserTO;
import com.example.http.HttpHandler;
import com.example.http.Wrapper;
import com.example.http.WrapperDeserializer;
import com.example.http.uri.Requests;
import com.example.ui.CustomPanel;
import com.example.ui.CustomTable;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ResourceController implements SwingController {
    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);
    JPanel main;
    JTable table = new CustomTable();

    private static SwingWorker<Void, Void> swingWorker;
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
        String resourcesJson = new HttpHandler().sendRequestAndGetJson(Requests.RESOURCES_ALL, null);
        String usersJson = new HttpHandler().sendRequestAndGetJson(Requests.USERS_ALL, null);
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Wrapper.class, new WrapperDeserializer());
        objectMapper.registerModule(module);
        try {
            resourceTOS = objectMapper.readValue(resourcesJson, ArrayList.class);
            usersTOS = objectMapper.readValue(usersJson, ArrayList.class);
        } catch (IOException e) {
            logger.error("exception at reading json", e);
            e.printStackTrace();
        }
        logger.info("users: " + usersTOS);
        logger.info("users array: " + Arrays.toString(usersTOS.toArray()));
    }

    private List<ResourceTO> getResourceTOS(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Wrapper.class, new WrapperDeserializer());
        objectMapper.registerModule(module);
        try {
            return objectMapper.readValue(json, new TypeReference<List<ResourceTO>>(){});
        } catch (IOException e) {
            logger.error("exception at reading json", e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void configuration() {
        this.main = new CustomPanel();
        main.setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);
        main.setLayout(new BorderLayout());
        updateTable();
    }

    public void updateTable() {
        List<ResourceTO> resources = getResourceTOS(new HttpHandler().sendRequestAndGetJson(Requests.RESOURCES_ALL, null));
        resourceTOS.clear();
        resourceTOS.addAll(resources);
        initTable(resourceTOS);
    }

    public void initTable(List<ResourceTO> resourceTOS) {
        SwingUtilities.invokeLater(() -> {
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
                        ResourceFormController.showEditWindow(table, row, ResourceController.this);
                    }
                }
            });

            JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem popupItem1 = new JMenuItem("edit resource");
            popupItem1.addActionListener(e -> {
                logger.info("get point: " + ((JMenuItem) e.getSource()).getLabel());
                ResourceFormController.showEditWindow(table, table.getSelectedRow(), ResourceController.this);
            });
            JMenuItem popupItem2 = new JMenuItem("delete resource");
            popupItem2.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    createDialog(table.getSelectedRow());
                }
            });

            JMenuItem popupItem3 = new JMenuItem("create new resource");
            popupItem3.addActionListener(e -> {
                ResourceFormController.showEditWindow(table, null, ResourceController.this);
            });

            popupMenu.add(popupItem1);
            popupMenu.add(popupItem2);
            popupMenu.add(popupItem3);
            table.setComponentPopupMenu(popupMenu);
        });

    }


    private void createDialog(int rowNum) {
        int result = JOptionPane.showConfirmDialog(getControllerPanel(), "pohui");
        logger.info("result: " + result);
        if (result == 0) {
            logger.info(rowNum + " rowNum");
            Integer idColumnValue = (Integer) table.getValueAt(rowNum, 0);
            ResourceTO res = new ResourceTO();
            res.setId(idColumnValue);
            deleteItem(res);
        } else {
            logger.info("nothing");
        }
    }

    private void deleteItem(ResourceTO res) {
        logger.info("delete item with id: " + res.getId());
        new HttpHandler().sendRequestAndGetJson(Requests.RESOURCES_DELETE, res);
        updateTable();
    }
}