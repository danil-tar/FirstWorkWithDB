package messenger.service;

import messenger.dto.User;
import messenger.repository.UserRepository;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Optional;

public class DeleteUserService {

    public String deleteUser(String email) {

        UserRepository userRepository = new UserRepository();

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
