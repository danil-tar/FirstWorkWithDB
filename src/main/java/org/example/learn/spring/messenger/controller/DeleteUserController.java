package org.example.learn.spring.messenger.controller;

import org.example.learn.spring.messenger.dto.User;
import org.example.learn.spring.messenger.service.DeleteUserService;
import org.example.learn.spring.messenger.service.JWTService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("messenger/deleteUser")
public class DeleteUserController {

    private JWTService jwtService;
    private DeleteUserService deleteUserService;

    private DeleteUserController(JWTService jwtService, DeleteUserService deleteUserService) {
        this.jwtService = jwtService;
        this.deleteUserService = deleteUserService;
    }


    @DeleteMapping("")
    public String doDelete(@RequestHeader String Jwt) {

        User user = jwtService.testValidity(Jwt);
        if (user != null) {
            return deleteUserService.deleteUser(user.getEmail());
        } else {
            return "result of deleting is false";
        }
    }
}

