package user;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Core {


    public static void main(String[] arg) throws SQLException {

        System.out.println("Testing connection to PostgreSQL JDBC");

        UserRepository userRepository = new UserRepository();

        Connection connection = userRepository.getConnectionToDB();

        System.out.println(connection.isValid(1));

        Statement statement = connection.createStatement();

        String query = "INSERT INTO users ( name, email, password) VALUES ('vika', 'vika@mail.ru', '9853');";
        boolean execute = statement.execute(query);
        System.out.println(execute);

//        userRepository.getUser("dan", "test@mail.ru", "testPass123");



    }
}
