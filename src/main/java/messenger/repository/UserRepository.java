package messenger.repository;

import messenger.dto.User;

import java.sql.*;

public class UserRepository {

    //  Database credentials
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/TestRegistration";
    static final String USER = "postgres";
    static final String PASS = "danil";

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void connectionToDB() {

        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
        }

        if (connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }
    }


    public User getUser(String email) throws SQLException {

        connectionToDB();
        Statement statement = connection.createStatement();

        String queryGet = String.format("SELECT * FROM public.users WHERE email='%s'", email);
        ResultSet resultSet = statement.executeQuery(queryGet);

        User user = null;
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            email = resultSet.getString(3);
            String password = resultSet.getString(4);
            user = new User(id, name, email, password);
            System.out.printf("%d. %s  %s %s\n", id, name, email, password);
        }

        return user;
    }


    public User createNewUser(User user) throws SQLException {

        connectionToDB();
        Statement statement = connection.createStatement();

        String queryPOST = String.format("INSERT INTO users ( name, email, password) VALUES ('%s', '%s', '%s');",
                user.getName(),
                user.getEmail(),
                user.getPassword());
        statement.execute(queryPOST);

        connection.close();

        return getUser(user.getEmail());
    }

    public void deleteUser(User user) throws SQLException {

        connectionToDB();
        Statement statement = connection.createStatement();

        String queryDELETE = String.format("DELETE FROM users WHERE id = '%d';", user.getId());
        statement.execute(queryDELETE);

        connection.close();
    }


}

