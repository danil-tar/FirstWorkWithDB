package messenger.service;

import messenger.controller.AuthorizationController;
import messenger.dto.User;
import messenger.repository.UserRepository;

import java.sql.SQLException;

public class AuthorizationService {

    public AuthorizationController.AuthorizationResponse authorizationUser(String requestedName,
                                                                           String requestedEmail,
                                                                           String requestedPassword) {

        String message;
        String nameInRepositoryUser;
        String passwordInRepositoryUser;
        String token = null;

        UserRepository userRepository = new UserRepository();
        JWTService jwtService = new JWTService();

        try {
            User repositoryUser = userRepository.getUser(requestedEmail);
            nameInRepositoryUser = repositoryUser.getName();
            passwordInRepositoryUser = repositoryUser.getPassword();

            if (nameInRepositoryUser.equals(requestedName)
                    && passwordInRepositoryUser.equals(requestedPassword)) {
                message = "Hello, " + requestedName + " you have successfully logged in!";
                token = jwtService.generateJWToken(nameInRepositoryUser, requestedEmail);
            } else {
                message = "Wrong name or password";
            }

        } catch (SQLException | NullPointerException e) {
            message = "User is not fund!!!";
            e.getStackTrace();
        }
        return new AuthorizationController.AuthorizationResponse(message, token);
    }

}
