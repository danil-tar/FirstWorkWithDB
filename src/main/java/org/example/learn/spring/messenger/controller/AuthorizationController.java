package org.example.learn.spring.messenger.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.learn.spring.messenger.service.AuthorizationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("messenger/login")
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    private AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @GetMapping
    public AuthorizationResponse doGet(@RequestParam("username") String name,
                                       @RequestParam(value = "email") String email,
                                       @RequestParam(name = "password") String password) {

        return authorizationService.authorizationUser(name, email, password);
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class AuthorizationResponse {
        private String result;
        private String token;
    }

}