package org.example.learn.spring.messenger.service;

import org.example.learn.spring.messenger.controller.AuthorizationController;
import org.example.learn.spring.messenger.dto.User;
import org.example.learn.spring.messenger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class AuthorizationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTService jwtService;

    private AuthorizationService() {
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
