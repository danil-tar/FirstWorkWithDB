package org.example.learn.spring.messenger.repository;

import org.example.learn.spring.messenger.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@Component
public class UserRepository {
    private UserRepository() {
    }

    @Autowired
    private ConnectionFactory connectionFactory;

    public Optional<User> getUser(String email) {
        Connection connection = connectionFactory.getConnection();

        Statement statement = null;
        User user = null;
        try {
            statement = connection.createStatement();
            String queryGet = String.format("SELECT * FROM users WHERE email='%s'", email);
            ResultSet resultSet = statement.executeQuery(queryGet);

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                email = resultSet.getString(3);
                String password = resultSet.getString(4);
                int partnerId = resultSet.getInt(5);
                user = new User(id, name, email, password);
                user.setPartnerId(partnerId);

//                System.out.printf("%d. %s  %s %s\n", id, name, email, password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Problem witch DB");
        }

        return Optional.ofNullable(user);
    }


    public User createNewUser(User user) throws SQLException {
        Connection connection = connectionFactory.getConnection();
        Statement statement = connection.createStatement();

        String queryPOST = String.format("INSERT INTO users ( \"name\", email, \"password\") VALUES ('%s', '%s', '%s');",
                user.getName(),
                user.getEmail(),
                user.getPassword());
        statement.execute(queryPOST);

        connection.close();

        return getUser(user.getEmail()).get();
    }

    public void deleteUser(User user) throws SQLException {
        Connection connection = connectionFactory.getConnection();
        Statement statement = connection.createStatement();

        String queryDELETE = String.format("DELETE FROM users WHERE id = '%d';", user.getId());
        statement.execute(queryDELETE);

        connection.close();
    }

    public void clear() {
        Connection connection = connectionFactory.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM users");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updatePartnerId(String email, Integer newUserPartnerId) {
        String querySetPartnerId = String.format("UPDATE users SET partner_id='%d' WHERE email='%s';", newUserPartnerId, email);

        Connection connection = connectionFactory.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.execute(querySetPartnerId);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}







