package com.hungerbet.hungerbet.controllers;

import com.hungerbet.hungerbet.controllers.models.billing.ReplenishBalanceRequest;
import com.hungerbet.hungerbet.entity.exceptions.BadRequestException;
import com.hungerbet.hungerbet.entity.exceptions.NotFoundException;
import com.hungerbet.hungerbet.service.PayService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/billings")
public class BillingController {
    @Autowired
    private PayService payService;

    @PostMapping
    public ResponseEntity create(@RequestBody ReplenishBalanceRequest request) {
        try {
            payService.ChangeBalance(request);
            return ResponseEntity.noContent().build();
        } catch (BadRequestException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
