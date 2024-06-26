package com.example.http.uri;

import client.to.LoginAnswer;
import client.to.ResourceTO;
import client.to.UserTO;
import client.to.chat.ChatTo;

import java.io.Serializable;

/**
 * хранилище URI для создания http запросов к серверу
 * значение добавляется к IKeys.DEFAULT_SERVER_URL
 */
public enum Requests {
    RESOURCES_ALL("resources/all", ResourceTO.class),
    RESOURCES_SAVE("resources/save", ResourceTO.class),
    RESOURCES_DELETE("resources/delete", null),
    CHAT_CREATE("chat", null),
    CHATS_FOR_USER("chat", ChatTo.class),
    USERS_ALL("users/all", UserTO.class),
    USERS_LOGIN("users/login", LoginAnswer.class),
    USERS_REGISTRATION("users/registration", String.class);

    private final String path;
    /**
     * класс, в который будем десериализовать полученный json
     */
    private final Class<? extends Serializable> desClass;

    public String getPath() {
        return path;
    }

    public Class<? extends Serializable> getDesClass() {
        return desClass;
    }

    Requests(String path, Class<? extends Serializable> desClass) {
        this.path = path;
        this.desClass = desClass;
    }
}
