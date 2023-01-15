import messenger.dto.User;
import messenger.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class TestDeleteController {

    @Test
    public void testDeleteController() throws SQLException {

        User user = new User(null, "danko", "danko345@mail.ru", "jfh46487");
        User newUser = UserRepository.getInstance().createNewUser(user);



    }
}
