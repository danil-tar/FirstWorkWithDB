import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestConnection {
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/TestRegistration";
    static final String USER = "postgres";
    static final String PASS = "danil";

    @Test
    public void shouldGetJdbcConnection() throws SQLException {
        try(Connection connection = DriverManager
                .getConnection(DB_URL, USER, PASS);) {
            assertTrue(connection.isValid(1));
            assertFalse(connection.isClosed());
        }

    }
}
