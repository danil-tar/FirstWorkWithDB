package messenger.service;

import messenger.dto.User;
import messenger.repository.ReferralRepository;
import messenger.repository.UserRepository;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;

public class ReferralService {

    private static ReferralService instance;

    private ReferralService() {
    }

    public static synchronized ReferralService getInstance() {
        if (instance == null) {
            instance = new ReferralService();
        }
        return instance;
    }

    private final Integer addToGeneratePartnerId = 1254;

    public Integer getPartnerId(String email) {
        UserRepository userRepository = UserRepository.getInstance();
        Optional<User> user = userRepository.getUser(email);
        if (user.isPresent()) {
            Integer partnerId = user.get().getPartnerId();
            return partnerId;
        }
        return 0;
    }

    public HashSet<String> getReferrals(String email) {

        HashSet<String> referralEmails = new HashSet<>();

        UserRepository userRepository = UserRepository.getInstance();
        Optional<User> user = userRepository.getUser(email);

        ReferralRepository referralRepository = ReferralRepository.getInstance();

        if (user.isPresent()) {
            referralEmails = referralRepository.getReferralEmails(user.get().getPartnerId());
        }
        return referralEmails;
    }

    public Integer generatePartnerId(String email) {

        UserRepository userRepository = UserRepository.getInstance();
        Integer partner_Id = 0;
        Optional<User> user = userRepository.getUser(email);
        if (user.isPresent() && user.get().getPartnerId() == 0) {
            partner_Id = user.get().getId() + addToGeneratePartnerId;
            return partner_Id;

        } else {
            if (user.isPresent()) {
                partner_Id = user.get().getPartnerId();
            }
        }
        return partner_Id;
    }

    public void registrationAsReferral(Integer partnerId, String emailForSettingPartnerId) {

        UserRepository userRepository = UserRepository.getInstance();
        ReferralRepository referralRepository = ReferralRepository.getInstance();
        try {
            Optional<User> userReferrer = referralRepository.getUserFromPartnerId(partnerId);
            Optional<User> userReferral = userRepository.getUser(emailForSettingPartnerId);
            if (userReferral.isPresent()) {
                referralRepository.setReferralId(userReferrer.get().getId(), userReferral.get().getId());
            }
        } catch (SQLException e) {
            System.out.println("Problem with DB");
            e.printStackTrace();
        }
    }


}

