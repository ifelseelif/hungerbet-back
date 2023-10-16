package com.hungerbet.hungerbet.controllers;

import com.hungerbet.hungerbet.controllers.models.auth.AuthRequest;
import com.hungerbet.hungerbet.controllers.models.auth.TokenResponseModel;
import com.hungerbet.hungerbet.controllers.models.auth.CreateUserRequest;
import com.hungerbet.hungerbet.entity.domain.User;
import com.hungerbet.hungerbet.entity.exceptions.HttpException;
import com.hungerbet.hungerbet.repository.UserRepository;
import com.hungerbet.hungerbet.service.implementaion.JwtService;
import com.hungerbet.hungerbet.service.implementaion.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public UUID register(@RequestBody CreateUserRequest userInfo) throws HttpException {
        return service.addUser(userInfo);
    }

    @PostMapping("/login")
    public TokenResponseModel authenticateAndGetToken(@RequestBody AuthRequest authRequest) throws HttpException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.getLogin());
            User user = userRepository.findByLogin(authRequest.getLogin()).orElseThrow(() -> new HttpException("User not found", HttpStatus.BAD_REQUEST));
            return new TokenResponseModel(token, user);
        } else {
            throw new HttpException("Wrong credentials", HttpStatus.UNAUTHORIZED);
        }
    }
}
