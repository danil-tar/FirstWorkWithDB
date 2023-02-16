package org.example.learn.spring.messenger.service;

import org.example.learn.spring.messenger.dto.User;
import org.example.learn.spring.messenger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;
@Service
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
