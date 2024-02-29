package com.example.config;

import java.io.IOException;
import java.util.Properties;

/**
 * класс, который загружает application.properties
 */
public class PropertiesHandler {
    private static final Properties properties = new Properties();

    private PropertiesHandler() {
    }

    public static void init() {
        try {
            properties.load(PropertiesHandler.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            throw new RuntimeException("can't load application properties");
        }
    }

    public static Properties getProperties() {
        return properties;
    }
}
