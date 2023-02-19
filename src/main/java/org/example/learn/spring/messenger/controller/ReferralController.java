package org.example.learn.spring.messenger.controller;

import org.example.learn.spring.messenger.service.ReferralService;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
@RequestMapping("messenger/referral")
public class ReferralController {
    private final ReferralService referralService;

    private ReferralController(ReferralService referralService) {
        this.referralService = referralService;
    }

    @GetMapping("myReferrals")
    public HashSet<String> getMyReferrals(@RequestParam(value = "email") String email) {

        return referralService.getReferrals(email);

    }

    @GetMapping("countReferrals")
    public int getAllCountReferrals(){
        return 0;
    }

    @PutMapping
    public String doPut(@RequestParam(value = "partnerId") Integer partnerId,
                      @RequestParam(value = "referralEmail") String referralEmail) {
        referralService.registrationAsReferral(partnerId, referralEmail);

        return"Referral added successfully";
    }

}
