package com.hungerbet.hungerbet.controllers;

import com.hungerbet.hungerbet.service.GameService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/managers")
@CrossOrigin(origins = "*")
public class ManagerController {

    @Autowired
    private GameService gameService;

    @GetMapping("/is-auth")
    @PreAuthorize("hasAuthority('Manager')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

}
