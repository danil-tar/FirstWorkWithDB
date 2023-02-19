package org.example.learn.spring.messenger.service;

import org.example.learn.spring.messenger.dto.User;
import org.example.learn.spring.messenger.repository.ReferralRepository;
import org.example.learn.spring.messenger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class CreateUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReferralRepository referralRepository;
    @Autowired
    private EMailService eMailService;
    @Autowired
    private ReferralService referralService;

    public String registrationNewUser(User user, String partnerId) {

        if (userRepository.getUser(user.getEmail()).isEmpty()) {

            if (partnerId == null || partnerId.isEmpty()) {
                partnerId = "0";
            }

            try {
                User userReferral = userRepository.createNewUser(user);
                Integer newUserPartnerId = referralService.generatePartnerId(userReferral.getEmail());
                userRepository.updatePartnerId(userReferral.getEmail(),newUserPartnerId);

                Optional<User> userFromPartnerId = referralRepository.getUserFromPartnerId(Integer.parseInt(partnerId));

                String message;
                if (userFromPartnerId.isPresent()) {
                    referralRepository.setReferralId(userFromPartnerId.get().getId(),
                            userRepository.getUser(userReferral.getEmail()).get().getId());
                    message = "User was created success witch partnerId = " + partnerId;
                } else {
                    message = "User was created,but PartnerId is not valid";
                }
                eMailService.sendEMail(user.getEmail(),
                        "Success registration",
                        "Welcome " + user.getName());

                return message;
            } catch (SQLException e) {
                System.out.println("Registration failed");
                e.getStackTrace();
            }
        }
        return "User witch this email is already registered";
    }
}
