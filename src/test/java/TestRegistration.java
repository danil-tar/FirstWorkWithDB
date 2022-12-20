import org.junit.Test;
import user.User;
import user.UserRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestRegistration {

    @Test
    public void testRegistration() throws SQLException {

        UserRepository userRepository = new UserRepository();

        Connection connectionToDB = userRepository.getConnectionToDB();
        User user = new User("testName", "1111@mail.ru", "Pass123");
        userRepository.createNewUser(user);
        userRepository.getUser(user.name);

        assertFalse(connectionToDB.isClosed());
    }
}


