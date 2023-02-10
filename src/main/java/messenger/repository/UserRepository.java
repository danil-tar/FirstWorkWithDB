package messenger.repository;

import messenger.dto.User;
import messenger.service.ReferralService;

import java.sql.*;
import java.util.Optional;

public class UserRepository {

    private static UserRepository instance = null;

    private UserRepository() {
    }

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

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


    public Optional<User> getUser(String email) {

        connectionToDB();

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

        connectionToDB();
        Statement statement = connection.createStatement();

        String queryPOST = String.format("INSERT INTO users ( name, email, password) VALUES ('%s', '%s', '%s');",
                user.getName(),
                user.getEmail(),
                user.getPassword());
        statement.execute(queryPOST);
        Integer partnerId = ReferralService.getInstance().generatePartnerId(user.getEmail());

        String querySetPartnerId = String.format("UPDATE users SET partner_id='%d' WHERE email='%s';", partnerId, user.getEmail());
        statement.execute(querySetPartnerId);

        connection.close();

        return getUser(user.getEmail()).get();
    }

    public void deleteUser(User user) throws SQLException {

        connectionToDB();
        Statement statement = connection.createStatement();

        String queryDELETE = String.format("DELETE FROM users WHERE id = '%d';", user.getId());
        statement.execute(queryDELETE);

        connection.close();
    }

    public void clear() {
        connectionToDB();
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
}







