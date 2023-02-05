package messenger.service;

import messenger.dto.User;
import messenger.repository.ReferralRepository;
import messenger.repository.UserRepository;

import java.sql.SQLException;
import java.util.Optional;

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

    public String registrationNewUser(User user, String partnerId) {

        UserRepository userRepository = UserRepository.getInstance();

        if (userRepository.getUser(user.getEmail()).isEmpty()) {

            if (partnerId == null || partnerId.isEmpty()) {
                partnerId = "0";
            }

            try {
                User userReferral = userRepository.createNewUser(user);
                ReferralRepository referralRepository = ReferralRepository.getInstance();

                Optional<User> userFromPartnerId = referralRepository.getUserFromPartnerId(Integer.parseInt(partnerId));

                String message;
                if (userFromPartnerId.isPresent()) {
                    referralRepository.setReferralId(userFromPartnerId.get().getId(),
                            userRepository.getUser(userReferral.getEmail()).get().getId());
                    message = "User was created success witch partnerId = " + partnerId;
                } else {
                    message = "User was created,but PartnerId is not valid";
                }
                EMailService.getInstance()
                        .sendEMail(user.getEmail(),
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
