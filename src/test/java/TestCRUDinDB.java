import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import user.User;
import user.UserRepository;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;


public class TestCRUDinDB {


//    @BeforeClass
    @Test
    public  void createRowsToDataBaseForTest() throws SQLException {

        UserRepository userRepository = new UserRepository();
        userRepository.getConnectionToDB();
        Statement statement = userRepository.getConnection().createStatement();

        statement.execute("DELETE FROM users");

        User user1 = new User(null, "dan", "dan@mail.ru", "123nj");
        User user2 = new User(null, "dan", "dan1253@mail.ru", "58rgg");
        User user3 = new User(null, "andi", "andi@mail.ru", "khjgyf14");
        User user4 = new User(null, "andi", "154andi@mail.ru", "pofu775mn");
        User user5 = new User(null, "Fan", "jhayt134@mail.ru", "iuf755f");

        userRepository.createNewUser(user1);
        userRepository.createNewUser(user2);
        userRepository.createNewUser(user3);
        userRepository.createNewUser(user4);
        userRepository.createNewUser(user5);

    }

    @Test
    public void testJdbcConnection() throws SQLException {
        UserRepository userRepository = new UserRepository();
        userRepository.getConnectionToDB();
        assertTrue(userRepository.getConnection().isValid(1));
        assertFalse(userRepository.getConnection().isClosed());
    }

    @Test
    public void testDeleteUserFromDataBase() throws SQLException {
        UserRepository userRepository = new UserRepository();
        userRepository.getConnectionToDB();

        userRepository.createNewUser(new User(null, "Faruh", "faruh@gmail.com", "faruh1234"));
        User user = userRepository.getUser("faruh@gmail.com");
        userRepository.deleteUser(user);

        User actualUser = userRepository.getUser(user.getEmail());

        Assert.assertEquals(null, actualUser);
    }


    @Test
    public void testRegistrationNewUserToDataBase() throws SQLException {

        UserRepository userRepository = new UserRepository();
        userRepository.getConnectionToDB();

        User user = new User(null, "testName", "333@mail.ru", "Pass8975");
        userRepository.createNewUser(user);
        User userActual = userRepository.getUser(user.getEmail());

        assertEquals(user.getName(), userActual.getName());
        assertEquals(user.getEmail(), userActual.getEmail());
        assertEquals(user.getPassword(), userActual.getPassword());
    }


//    @AfterClass
//    public static void clearAllRowsInDataBaseAfterTest() throws SQLException {
//
//        UserRepository userRepository = new UserRepository();
//        userRepository.getConnectionToDB();
//        Statement statement = userRepository.getConnection().createStatement();
//
//        statement.execute("DELETE FROM users");
//
//    }
}



