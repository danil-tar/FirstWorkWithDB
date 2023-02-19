package org.example.learn.spring.messenger.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionFactory {
    @Value("${DB_URL}")
    private static String DB_URL;
    @Value("${USER}")
    private static String USER;
    @Value("${PASS}")
    private static String PASS;

    private ConnectionFactory() {
    }

    public Connection getConnection() {
        return connectToDb();
    }


    private Connection connectToDb() {
        try {
            Connection connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);
            if (connection != null) {
                return connection;
            } else {
                throw new RuntimeException("Connection Failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Connection Failed", e);
        }
    }

    public static void changeDbURL(String newUrl) {
        DB_URL = newUrl;
    }
}
