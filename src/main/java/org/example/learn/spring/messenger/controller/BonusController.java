package org.example.learn.spring.messenger.controller;

import org.example.learn.spring.messenger.service.BonusService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("messenger/bonus")
public class BonusController {
    private BonusService bonusService;

    private BonusController(BonusService bonusService) {
        this.bonusService = bonusService;
    }

    @GetMapping("")
    public String getBonusForUserByEmail(@RequestParam(value = "email") String email) {
       return bonusService.countingBonusForUser("email");
    }

}
