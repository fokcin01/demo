package com.example.http.uri;

/**
 * хранилище URI для создания http запросов к серверу
 * значение добавляется к IKeys.DEFAULT_SERVER_URL
 */
public enum Requests {
    RESOURCES_ALL("resources/all"),
    USERS_ALL("users/all");
    private final String path;

    public String getPath() {
        return path;
    }

    Requests(String path) {
        this.path = path;
    }
}
