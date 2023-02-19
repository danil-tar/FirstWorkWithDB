package org.example.learn.spring.messenger.service;

import org.example.learn.spring.messenger.dto.User;
import org.example.learn.spring.messenger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class BonusService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReferralService referralService;
    public String countingBonusForUser(String email) {
        Optional<User> user = userRepository.getUser(email);
        int bonus = 0;
        if (user.isPresent()) {
            HashSet<String> referrals = referralService.getReferrals(email);
           int size = referrals.size();
            if (size == 0) {
            } else if (size <= 10) {
                bonus = 1;
            } else if (size <= 20) {
                bonus = 2;
            } else if (size <= 30) {
                bonus = 3;
            } else {
                bonus = 5;
            }
        }
        return "Bonus user wish " + email + " is " + bonus;
    }

}
