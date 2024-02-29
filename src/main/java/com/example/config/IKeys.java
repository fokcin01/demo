package com.example.config;

/**
 * enum с ключами из файла application.properties
 */
public enum IKeys {
    DEFAULT_SERVER_URL("default.server.url");

    private final String value;

    IKeys(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
