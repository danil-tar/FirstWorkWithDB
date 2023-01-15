package messenger.service;

import messenger.dto.User;
import messenger.repository.UserRepository;

import java.sql.SQLException;

public class CreateUserService {

    private static CreateUserService instance = null;

    private CreateUserService() {
    }

    public static synchronized CreateUserService getInstance() {
        if (instance == null) {
            instance = new CreateUserService();
        }
        return instance;
    }

    public String registrationNewUser(User user) {

        UserRepository userRepository = UserRepository.getInstance();
        try {
            if (userRepository.getUser(user.getEmail()).isEmpty()) {
                userRepository.createNewUser(user);
                return "User registered success";
            }
        } catch (SQLException e) {
            System.out.println("Registration failed");
            e.getStackTrace();
        }

        return "User witch this email is already registered";
    }
}
