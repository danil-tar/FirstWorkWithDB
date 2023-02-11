import messenger.dto.User;
import messenger.repository.ConnectionFactory;
import messenger.repository.ReferralRepository;
import messenger.repository.UserRepository;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.*;


public class TestCRUDinDB {
        @AfterClass
        public  static void deletAllTables(){
            InitDb.clearDB();
        }
    @BeforeClass
//    @Test
    public static void createRowsToDataBaseForTest() throws SQLException {
        InitDb.createTables();
        UserRepository userRepository = UserRepository.getInstance();
        ReferralRepository referralRepository = ReferralRepository.getInstance();


        userRepository.clear();
        referralRepository.clear();

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

        Integer idReferrer = userRepository.getUser("dan@mail.ru").get().getId();
        Integer idReferral1 = userRepository.getUser("dan1253@mail.ru").get().getId();
        Integer idReferral2 = userRepository.getUser("andi@mail.ru").get().getId();

        referralRepository.setReferralId(idReferrer, idReferral1);
        referralRepository.setReferralId(idReferrer, idReferral2);

    }

    @Test
    public void testJdbcConnection() throws SQLException {
        Connection connection = ConnectionFactory.getInstance().getConnection();
        assertTrue(connection.isValid(1));
        assertFalse(connection.isClosed());
    }

    @Test
    public void testDeleteUserFromDataBase() throws SQLException {
        UserRepository userRepository = UserRepository.getInstance();

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

        UserRepository userRepository = UserRepository.getInstance();

        User user = new User(null, "testName", "333@mail.ru", "Pass8975");
        User userActual = userRepository.createNewUser(user);

        assertEquals(user.getName(), userActual.getName());
        assertEquals(user.getEmail(), userActual.getEmail());
        assertEquals(user.getPassword(), userActual.getPassword());
    }


//    @AfterClass
//    public static void clearAllRowsInDataBaseAfterTest() throws SQLException {
//
//        userRepository = UserRepository.getInstance();
//        userRepository.getConnectionToDB();
//        Statement statement = userRepository.getConnection().createStatement();
//
//        statement.execute("DELETE FROM users");
//
//    }
}



