import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

//STEP 1. Import required packages

public class UserRepository {

    //  Database credentials
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/TestRegistration";
    static final String USER = "postgres";
    static final String PASS = "danil";

    public static void main(String[] arg) throws SQLException {

        System.out.println("Testing connection to PostgreSQL JDBC");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return;
        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");
        Connection connection = null;

        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }
        System.out.println(connection.isValid(1));
        Statement statement = connection.createStatement();

        String query = "INSERT INTO users ( name, email, password) VALUES ('dan', 'hfyfb@mail.ru', '11111');";

        boolean execute = statement.execute(query);
        System.out.println(execute);
    }


    private void getExecutor(String name){

    }

    private void postExecutor(String name){

    }

    private void putExecutor(String name){

    }

    private void deleteExecutor(String name){

    }


}

