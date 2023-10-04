package com.hungerbet.hungerbet.controllers;

import com.hungerbet.hungerbet.entity.domain.User;
import com.hungerbet.hungerbet.entity.exceptions.NotFoundException;
import com.hungerbet.hungerbet.repository.UserRepository;
import com.hungerbet.hungerbet.service.implementaion.UserInfoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private UserInfoService service;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/is-auth")
    public String IsAuthorized() {
        return "Authorized";
    }

    @GetMapping
    public User getUser(@RequestParam String login) throws NotFoundException {
        User user =  userRepository.findByLogin(login).orElseThrow(() -> new NotFoundException("Not found"));
        if(user.getManager() != null) {
            user.getManager().setUser(null);
            user.getManager().setGame(null);
        }
        return user;
    }
}
