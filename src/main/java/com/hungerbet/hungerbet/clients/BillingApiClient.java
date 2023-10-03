package com.hungerbet.hungerbet.clients;

import org.springframework.http.ResponseEntity;

public interface BillingApiClient {
    ResponseEntity debitingMoney(String login, double amount);
}
