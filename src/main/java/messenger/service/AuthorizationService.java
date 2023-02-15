package messenger.service;

import messenger.annotation.Autowired;
import messenger.annotation.Singleton;
import messenger.controller.AuthorizationController;
import messenger.dto.User;
import messenger.menegment.InstanceFactory;
import messenger.repository.UserRepository;

import java.util.NoSuchElementException;

@Singleton(lazy = true)
public class AuthorizationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTService jwtService;

    private AuthorizationService() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public AuthorizationController.AuthorizationResponse authorizationUser(String requestedName,
                                                                           String requestedEmail,
                                                                           String requestedPassword) {

        String message;
        String nameInRepositoryUser;
        String passwordInRepositoryUser;
        String token = null;

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

        } catch (NoSuchElementException e) {
            message = "User is not fund!!!";
            e.getStackTrace();
        }
        return new AuthorizationController.AuthorizationResponse(message, token);
    }

}
