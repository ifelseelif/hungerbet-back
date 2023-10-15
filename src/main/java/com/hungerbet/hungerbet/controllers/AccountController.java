package com.hungerbet.hungerbet.controllers;

import com.hungerbet.hungerbet.controllers.models.billing.ReplenishBalanceRequest;
import com.hungerbet.hungerbet.controllers.models.users.AccountResponseModel;
import com.hungerbet.hungerbet.entity.domain.User;
import com.hungerbet.hungerbet.entity.exceptions.HttpException;
import com.hungerbet.hungerbet.repository.UserRepository;
import com.hungerbet.hungerbet.service.PayService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/account")
@CrossOrigin(origins = "*")
public class AccountController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PayService payService;

    @GetMapping
    public AccountResponseModel getAccount(Principal principal) throws HttpException {
        String login = principal.getName();
        User user = userRepository.findByLogin(login).orElseThrow(() -> new HttpException("User not found", HttpStatus.BAD_REQUEST));
        return new AccountResponseModel(user.getBalanceMoney());
    }

    @GetMapping("/balance")
    public AccountResponseModel getBalance(Principal principal) throws HttpException {
        String login = principal.getName();
        User user = userRepository.findByLogin(login).orElseThrow(() -> new HttpException("User not found", HttpStatus.BAD_REQUEST));
        return new AccountResponseModel(user.getBalanceMoney());
    }

    @PostMapping("/deposit")
    public AccountResponseModel depositMoney(@RequestBody ReplenishBalanceRequest request, Principal principal) throws HttpException {
        String login = principal.getName();

        double balance = payService.ChangeBalance(login, request.getAmount());
        return new AccountResponseModel(balance);
    }
}
