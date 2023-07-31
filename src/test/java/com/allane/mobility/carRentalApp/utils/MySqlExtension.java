package com.allane.mobility.carRentalApp.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.MySQLContainer;

import java.util.Properties;

@Slf4j
public class MySqlExtension  implements BeforeAllCallback, AfterAllCallback {

    private MySQLContainer<?> sqlContainer;
    private final static Properties config = new Properties();
    private final static String PROPERTIES = "application.yaml";
    private static String DB_NAME;

    static {
        try {
            config.load(MySqlExtension.class.getClassLoader().getResourceAsStream(PROPERTIES));
            DB_NAME = config.getProperty("url");
            if (DB_NAME == null) {
                throw new RuntimeException();
            }
            DB_NAME = DB_NAME.substring(DB_NAME.lastIndexOf("/") + 1);
        } catch (Exception e) {
            log.error("Error reading application.yaml");
        }
    }

    @Override
    public void beforeAll(ExtensionContext context) {

        sqlContainer = new MySQLContainer<>("mysql")
                .withExposedPorts(3306)
                .withDatabaseName(DB_NAME)
                .withUsername(config.getProperty("username"))
                .withPassword(config.getProperty("password"));

        sqlContainer.start();
        String jdbcUrl = String.format("jdbc:mysql://localhost:%d/%s", sqlContainer.getFirstMappedPort(), DB_NAME);
        System.setProperty("spring.datasource.url", jdbcUrl);
        System.setProperty("spring.datasource.username", config.getProperty("username"));
        System.setProperty("spring.datasource.password", config.getProperty("password"));

        log.info("spring.datasource.url {}", jdbcUrl);

    }

    @Override
    public void afterAll(ExtensionContext context) {

    }

}
