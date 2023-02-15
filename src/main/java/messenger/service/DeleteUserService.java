package messenger.service;

import messenger.annotation.Autowired;
import messenger.annotation.Singleton;
import messenger.dto.User;
import messenger.repository.UserRepository;

import java.sql.SQLException;
import java.util.Optional;
@Singleton
public class DeleteUserService {

    @Autowired
    private  UserRepository userRepository;

    private DeleteUserService() {
    }

    public String deleteUser(String email) {


        Optional<User> user;
        user = userRepository.getUser(email);
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

    public void clear() {
        userRepository.clear();
    }
}
