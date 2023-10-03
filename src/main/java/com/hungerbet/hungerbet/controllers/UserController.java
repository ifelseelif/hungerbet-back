package com.hungerbet.hungerbet.controllers;

import com.hungerbet.hungerbet.service.implementaion.UserInfoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private UserInfoService service;

    @GetMapping("/is-auth")
    public String IsAuthorized() {
        return "Authorized";
    }
}
