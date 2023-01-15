package messenger.service;

import messenger.dto.User;
import messenger.repository.UserRepository;

import java.sql.SQLException;
import java.util.Optional;

public class DeleteUserService {

    private static DeleteUserService instance = null;

    private DeleteUserService() {
    }

    public static synchronized DeleteUserService getInstance() {
        if (instance == null) {
            instance = new DeleteUserService();
        }
        return instance;
    }

    public String deleteUser(String email) {

        UserRepository userRepository = UserRepository.getInstance();

        Optional<User> user;
        try {
            user = userRepository.getUser(email);
        } catch (SQLException e) {
            e.printStackTrace();
            return "Problem witch access DB";
        }
        if (user.isPresent()) {
            try {
                userRepository.deleteUser(user.get());
                return "User witch email" + email + " was deleted";
            } catch (SQLException e) {
                e.printStackTrace();
                return "Deleting User witch email" + email + "is failed";
            }
        }
        return "User witch email" + email + " not fund";
    }
}
