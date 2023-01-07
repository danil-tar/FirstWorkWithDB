package messenger.service;

import messenger.dto.User;
import messenger.repository.UserRepository;

import java.sql.SQLException;

public class CreateUserService {
    public CreateUserService() {
    }

    public String registrationNewUser(String newUserName, String newUserEmail, String newUserPassword) {

        UserRepository userRepository = new UserRepository();
        try {
            if (userRepository.getUser(newUserEmail) == null) {
                userRepository.createNewUser(new User(null, newUserName, newUserEmail, newUserPassword));
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
