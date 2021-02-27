package com.project.carrental.config;

import org.apache.log4j.Logger;

import java.util.ResourceBundle;

/**
 * Class for managing outer resources for database connection and web pages
 * addressing
 *
 */
public class ConfigManager {
    public static final Logger LOGGER = Logger.getLogger(ConfigManager.class);
    private static ConfigManager instance;
    private ResourceBundle resourceBundle;

    private static final String BUNDLE_NAME = "config";
    public static final String DATABASE_DRIVER_NAME = "DATABASE_DRIVER_NAME";
    public static final String DATABASE_URL = "DATABASE_URL";
    public static final String DATABASE_USER = "DATABASE_USER";
    public static final String DATABASE_PASSWORD = "DATABASE_PASSWORD";

    public static final String INDEX_PAGE_PATH = "INDEX_PAGE_PATH";
    public static final String LOGIN_PAGE_PATH = "LOGIN_PAGE_PATH";
    public static final String REGISTRATION_PAGE_PATH = "REGISTRATION_PAGE_PATH";
    public static final String ORDER_PAGE_PATH = "ORDER_PAGE_PATH";
    public static final String ADMIN_PAGE_PATH = "ADMIN_PAGE_PATH";
    public static final String ERROR_PAGE_PATH = "ERROR_PAGE_PATH";
    public static final String INFO_ORDER_PAGE_PATH = "INFO_ORDER_PAGE_PATH";
    public static final String INFO_REG_PAGE_PATH = "INFO_REG_PAGE_PATH";

    public static synchronized ConfigManager getInstance() {
        if (instance == null) {
            instance = new ConfigManager();
            instance.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
        }
        return instance;
    }

    public String getProperty(String key) {
        LOGGER.debug("Config called by key: " + key);
        return resourceBundle.getString(key);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Singleton");
    }
}