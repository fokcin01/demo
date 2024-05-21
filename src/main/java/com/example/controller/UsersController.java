package com.example.controller;

import client.to.UserTO;
import com.example.Login;
import com.example.Services.ImageReader;
import com.example.http.HttpHandler;
import com.example.http.uri.Requests;
import com.example.ui.CustomPanel;
import com.example.ui.CustomTable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static com.example.controller.ResourceFormController.createConstraint;

public class UsersController implements SwingController {
    JPanel panel;
    private List<UserTO> userTOS;
    ImageReader imReader = new ImageReader();

    public UsersController() {
        init();
    }

    @Override
    public JPanel getControllerPanel() {
        return panel;
    }

    @Override
    public void fillData() {
        userTOS = new HttpHandler<List<UserTO>>().sendRequest(Requests.USERS_ALL, null);
        System.out.println("tos: " + userTOS);
    }

    public void initTable(List<UserTO> userTOS) {
        JTable usersTable = new CustomTable();
        panel.add(usersTable, BorderLayout.CENTER);
        usersTable.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"id", "user"}));
        for (UserTO to: userTOS) {
            ((DefaultTableModel)usersTable.getModel()).addRow(new Object[] {
                    to.getId(), to.getUsername()
            });
        }
        panel.add(usersTable.getTableHeader(), BorderLayout.NORTH);
        panel.add(usersTable, BorderLayout.CENTER);
    }

    @Override
    public void configuration() {
        this.panel = new CustomPanel();
        panel.setLayout(new BorderLayout());
        initTable(userTOS);
    }
    public void Registration() {
        JFrame regFrame = new JFrame();
        regFrame.setTitle("Registration");
        regFrame.setLayout(new BorderLayout());
        regFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        regFrame.setSize(400, 250);
        regFrame.setResizable(false);          // эта хуйня запрещает менять размер
        regFrame.setLocationRelativeTo(null);  // эта хуйня центрует
        regFrame.setVisible(true);

        JPanel panelWithLabelsAndFields = new CustomPanel();
        panelWithLabelsAndFields.setLayout(new GridBagLayout());
        regFrame.add(panelWithLabelsAndFields, BorderLayout.CENTER);

        int top = 10, left = 10, bottom = 10, right = 10;
        panelWithLabelsAndFields.setBorder(new EmptyBorder(top, left, bottom, right));

        JTextField loginField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JLabel loginLabel = new JLabel("login");
        JLabel passLabel = new JLabel("password");

        panelWithLabelsAndFields.add(loginLabel, createConstraint(0, 1));
        panelWithLabelsAndFields.add(loginField, createConstraint(1, 1));
        panelWithLabelsAndFields.add(passLabel, createConstraint(0, 2));
        panelWithLabelsAndFields.add(passField, createConstraint(1, 2));

        JPanel panelWithButtons = new CustomPanel();
        panelWithButtons.setLayout(new GridBagLayout());
        regFrame.add(panelWithButtons, BorderLayout.SOUTH);

        JButton enterButton = new JButton("enter");
        enterButton.setIcon(imReader.createImageIcon("enter.png", 16, 16));
        enterButton.addActionListener(e -> {
            String login = loginField.getText();
            String password = passField.getText();

            if (login.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(regFrame, "Login or password are required", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                regFrame.dispose();
            }
        });

        JButton cancelButton = new JButton("cancel");
        cancelButton.setIcon(imReader.createImageIcon("cancel.png", 16, 16));
        cancelButton.addActionListener(e -> regFrame.dispose());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelWithButtons.add(enterButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panelWithButtons.add(cancelButton, gbc);

        JLabel autlink = createAuthorizationLink(regFrame);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panelWithButtons.add(autlink, gbc);

    }

    public JLabel createAuthorizationLink(JFrame currentFrame) {
        JLabel autlink = new JLabel("<html><a href=''>Has account? Sign in </a></html>");
        autlink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        autlink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentFrame.dispose();
                try {
                    Login log = new Login();
                    log.run();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        return autlink;
    }
}
