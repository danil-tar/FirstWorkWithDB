package user;

import java.sql.*;

//STEP 1. Import required packages

public class UserRepository {

    //  Database credentials
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/TestRegistration";
    static final String USER = "postgres";
    static final String PASS = "danil";

    private Connection connection;


    public Connection getConnectionToDB() {

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
        return connection;
    }


    public void getUser(String name) throws SQLException {


        Statement statement = connection.createStatement();

        String queryGet = String.format("SELECT * FROM public.users WHERE name='%s'", name);

//        tom'; DELETE FROM users WHERE ''='

        ResultSet resultSet = statement.executeQuery(queryGet);
        System.out.println(resultSet);

        while (resultSet.next()) {

            int id = resultSet.getInt(1);
             String email= resultSet.getString(3);
//            System.out.printf("%d. %s - %s \n", id, name, email);
            System.out.printf("%d. %s  %s\n", id, name, email );
        }
    }

    public void createNewUser(User user) throws SQLException {

        Statement statement = connection.createStatement();

        String queryPOST = String.format("INSERT INTO users ( name, email, password) VALUES ('%s', '%s', '%s');",
                user.name, user.email, user.password);

        statement.execute(queryPOST);

    }

    private void executorPUT(String name) {

    }

    private void executorDELETE(String name) {

    }


}

