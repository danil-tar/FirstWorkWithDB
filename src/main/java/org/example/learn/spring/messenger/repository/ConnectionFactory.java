package org.example.learn.spring.messenger.repository;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class ConnectionFactory {
    private static String DB_URL = "jdbc:postgresql://127.0.0.1:5432/TestRegistration";
    private static final String USER = "postgres";
    private static final String PASS = "danil";

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
