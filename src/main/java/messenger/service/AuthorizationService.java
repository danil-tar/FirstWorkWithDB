package messenger.service;

import messenger.dto.User;
import messenger.repository.UserRepository;

import java.sql.SQLException;

public class AuthorizationService {

    public String authorizationUser(String nameRequest, String emailRequest, String passwordRequest) {

        String message;
        String nameRepositoryUser;
        String passwordRepositoryUser;

        UserRepository userRepository = new UserRepository();
        try {
            User repositoryUser = userRepository.getUser(emailRequest);
            nameRepositoryUser = repositoryUser.getName();
            passwordRepositoryUser = repositoryUser.getPassword();

            if (nameRepositoryUser.equals(nameRequest)
                    && passwordRepositoryUser.equals(passwordRequest)) {
                message = "Hello, " + nameRequest + " you have successfully logged in!";
            } else {
                message = "Wrong name or password";
            }

        } catch (SQLException | NullPointerException e) {
            message = "User is not fund!!!";
            e.getStackTrace();
        }
        return message;
    }
}
