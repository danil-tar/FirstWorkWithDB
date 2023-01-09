package messenger.service;

import messenger.dto.User;
import messenger.repository.UserRepository;

import java.io.PrintWriter;
import java.sql.SQLException;

public class DeleteUserService {

     public String deleteUser(String email, String password) {

        UserRepository userRepository = new UserRepository();

        User user;
        try {
            user = userRepository.getUser(email);
        } catch (SQLException e) {
            e.printStackTrace();
            return "User witch email" + email + " not fund";
        }

        if (user.getPassword().equals(password)) {
            try {
                userRepository.deleteUser(user);
            } catch (SQLException e) {
                e.printStackTrace();
                return "Deleting User witch email" + email + "is failed";
            }
        }
        return "User witch email" + email + " was deleted";
    }
}
