import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import messenger.dto.User;
import messenger.repository.UserRepository;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.Assert.*;


public class TestCRUDinDB {


    @BeforeClass
//    @Test
    public static void createRowsToDataBaseForTest() throws SQLException {

        UserRepository userRepository = new UserRepository();
        userRepository.connectionToDB();
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
        userRepository.connectionToDB();
        assertTrue(userRepository.getConnection().isValid(1));
        assertFalse(userRepository.getConnection().isClosed());
    }

    @Test
    public void testDeleteUserFromDataBase() throws SQLException {
        UserRepository userRepository = new UserRepository();

        User user = userRepository.createNewUser(new User(null,
                "Faruh",
                "faruh@gmail.com",
                "faruh1234"));

        userRepository.deleteUser(user);

        Optional<User> actualUser = userRepository.getUser(user.getEmail());

        Assert.assertTrue(actualUser.isEmpty());
    }


    @Test
    public void testRegistrationNewUserToDataBase() throws SQLException {

        UserRepository userRepository = new UserRepository();

        User user = new User(null, "testName", "333@mail.ru", "Pass8975");
        User userActual = userRepository.createNewUser(user);

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



