package com.example.controller.chat;

import client.to.ResourceTO;
import client.to.UserTO;
import client.to.chat.ChatTo;
import com.example.Application;
import com.example.controller.ResourceController;
import com.example.controller.SwingController;
import com.example.http.HttpHandler;
import com.example.http.Wrapper;
import com.example.http.WrapperDeserializer;
import com.example.http.uri.Requests;
import com.example.ui.CustomPanel;
import com.example.ui.CustomTable;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class ChatController implements SwingController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    JPanel main;
    private JTable chatTable;
    private List<ChatTo> userChats;

    public ChatController() {
        init();
    }

    @Override
    public JPanel getControllerPanel() {
        return main;
    }

    @Override
    public void fillData() {
        userChats = getChatsTOS(new HttpHandler().sendRequestAndGetJson(Requests.CHATS_FOR_USER, null));

    }

    private List<ChatTo> getChatsTOS(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Wrapper.class, new WrapperDeserializer());
        objectMapper.registerModule(module);
        try {
            return objectMapper.readValue(json, new TypeReference<List<ChatTo>>(){});
        } catch (IOException e) {
            logger.error("exception at reading json", e);
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void configuration() {
        main = new CustomPanel();
        main.setLayout(new BorderLayout());
        chatTable = new CustomTable();

        JButton createChat = new JButton("new chat");
        UserTO me = new UserTO(2);
        UserTO notMe = new UserTO(1);
        Set<UserTO> set = new HashSet<>();
        set.add(me);
        set.add(notMe);
        ChatTo stub = new ChatTo(null, false, set, null);
        createChat.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                ChatTo chatTo = new HttpHandler().sendRequestAndGetJson(Requests.CHAT_CREATE, stub);
//                if (chatTo != null) {
//                    updateChatsTable();
//                }
            }
        });
        main.add(createChat, BorderLayout.NORTH);
        main.add(chatTable, BorderLayout.CENTER);
        updateChatsTable();
    }

    public void updateChatsTable() {
        userChats = getChatsTOS(new HttpHandler().sendRequestAndGetJson(Requests.CHATS_FOR_USER, null));
        initChatsTable(userChats);
    }

    private String defineChatName(ChatTo chat) {
        //если чат не групповой, то название - имя второго участника
        if (!chat.isGroup()) {
            UserTO notMe = chat.getParticipants()
                    .stream()
                    .filter(userTO -> !userTO.getId().equals(Application.getLoggedUserId()))
                    .findFirst().orElse(null);
            if (notMe != null) {
                return notMe.getUsername();
            } else return "Имя не найдено";
        } else return "заглушка для имени группы";
    }

    public void initChatsTable(List<ChatTo> chats) {
        chatTable.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"chatName"}));
        for (ChatTo to : chats) {
            ((DefaultTableModel) chatTable.getModel()).addRow(new Object[]{defineChatName(to)});
        }
    }
}
