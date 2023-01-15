package messenger.service;

import messenger.controller.AuthorizationController;
import messenger.dto.User;
import messenger.repository.UserRepository;

import java.sql.SQLException;
import java.util.NoSuchElementException;

public class AuthorizationService {

    private static AuthorizationService instance = null;

    private AuthorizationService() {
    }

    public static synchronized AuthorizationService getInstance() {
        if (instance == null) {
            instance = new AuthorizationService();
        }
        return instance;
    }

    public AuthorizationController.AuthorizationResponse authorizationUser(String requestedName,
                                                                           String requestedEmail,
                                                                           String requestedPassword) {

        String message;
        String nameInRepositoryUser;
        String passwordInRepositoryUser;
        String token = null;

        UserRepository userRepository = UserRepository.getInstance();
        JWTService jwtService = JWTService.getInstance();

        try {
            User repositoryUser = userRepository.getUser(requestedEmail).get();
            nameInRepositoryUser = repositoryUser.getName();
            passwordInRepositoryUser = repositoryUser.getPassword();

            if (nameInRepositoryUser.equals(requestedName)
                    && passwordInRepositoryUser.equals(requestedPassword)) {
                message = "Hello, " + requestedName + " you have successfully logged in!";
                token = jwtService.generateJWToken(nameInRepositoryUser, requestedEmail);
            } else {
                message = "Wrong name or password";
            }

        } catch (SQLException | NoSuchElementException e) {
            message = "User is not fund!!!";
            e.getStackTrace();
        }
        return new AuthorizationController.AuthorizationResponse(message, token);
    }

}
