package org.example.learn.spring.messenger.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.learn.spring.messenger.dto.User;
import org.example.learn.spring.messenger.service.CreateUserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("messenger/registration")
public class RegistrationUserController {

    private final CreateUserService createUserService;

    private RegistrationUserController(CreateUserService createUserService) {
        this.createUserService = createUserService;
    }

    @PostMapping
    protected RegistrationResponse doPost(@RequestBody User user,
                                          @RequestParam(value = "partnerId", required = false) String partnerIdFromReq) {

        String resultRegistration = createUserService.registrationNewUser(user, partnerIdFromReq);
        return new RegistrationResponse(resultRegistration);
    }

    @Setter
    @Getter
    @AllArgsConstructor
    static class RegistrationResponse {
        private String result;
    }
}
