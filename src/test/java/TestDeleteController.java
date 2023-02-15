import messenger.dto.User;
import messenger.menegment.InstanceFactory;
import messenger.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class TestDeleteController {

    @Test
    public void testDeleteController() throws SQLException {

        User user = new User(null, "danko", "danko345@mail.ru", "jfh46487");
        User newUser = InstanceFactory.getInstance(UserRepository.class).createNewUser(user);


    }
}
