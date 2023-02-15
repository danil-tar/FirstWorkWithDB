package messenger.service;

import messenger.annotation.Autowired;
import messenger.annotation.Singleton;
import messenger.dto.User;
import messenger.menegment.InstanceFactory;
import messenger.repository.ReferralRepository;
import messenger.repository.UserRepository;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
@Singleton
public class ReferralService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReferralRepository referralRepository;
    private ReferralService() {
    }

    private final Integer addToGeneratePartnerId = 1254;

    public Integer getPartnerId(String email) {
        Optional<User> user = userRepository.getUser(email);
        if (user.isPresent()) {
            Integer partnerId = user.get().getPartnerId();
            return partnerId;
        }
        return 0;
    }

    public HashSet<String> getReferrals(String email) {

        HashSet<String> referralEmails = new HashSet<>();

        Optional<User> user = userRepository.getUser(email);

        if (user.isPresent()) {
            referralEmails = referralRepository.getReferralEmails(user.get().getPartnerId());
        }
        return referralEmails;
    }

    public Integer generatePartnerId(String email) {

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

