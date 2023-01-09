package messenger.service;

import messenger.dto.User;
import messenger.repository.UserRepository;

import java.sql.SQLException;

public class CreateUserService {

    public String registrationNewUser(User user) {

        UserRepository userRepository = new UserRepository();
        try {
            if (userRepository.getUser(user.getEmail()) == null) {
                userRepository.createNewUser(user);
                return "User registered success";
            }
        } catch (SQLException e) {
            {
                System.out.println("Registration failed");
                e.getStackTrace();
            }
        }

        return "User witch this email is already registered";
    }
}
