package messenger.service;

import messenger.dto.User;
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
        Integer partnerId = 0;
        try {
            Optional<User> user = userRepository.getUser(email);
            if (user.isPresent()) {
                partnerId = user.get().getPartnerId();
                return partnerId;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Problem witch DB");
        }
        return partnerId;
    }


    public Optional<HashSet<Integer>> getReferrals(String email) {

        UserRepository userRepository = UserRepository.getInstance();
        userRepository.connectionToDB();
        HashSet<Integer> referralSet = null;
        try {
            Optional<User> user = userRepository.getUser(email);
            if (user.isPresent()) {
                Integer partnerId = user.get().getId();
                referralSet = userRepository.getReferralSet(partnerId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(referralSet);
    }


    public Integer generatePartnerId(String email) {

        UserRepository userRepository = UserRepository.getInstance();
        Integer partner_Id = null;
        try {
            Optional<User> user = userRepository.getUser(email);
            if (user.isPresent() && user.get().getPartnerId() == null) {
                partner_Id = user.get().getId() + addToGeneratePartnerId;
                return partner_Id;

            } else {
                if (user.isPresent()) {
                    partner_Id = user.get().getPartnerId();
                }
            }
        } catch (SQLException e) {
            System.out.println("Problem with connectivity");
            e.printStackTrace();
        }
        return partner_Id;
    }


    public void settingPartnerId(Integer partnerId, String emailForSettingPartnerId) {

        UserRepository userRepository = UserRepository.getInstance();
        try {
            Optional<User> user = userRepository.getUser(emailForSettingPartnerId);
            if (user.isPresent()) {
                Integer referralId = user.get().getId();
                userRepository.setReferralId(partnerId, referralId);
            }
        } catch (SQLException e) {
            System.out.println("Problem with DB");
            e.printStackTrace();
        }
    }


}

