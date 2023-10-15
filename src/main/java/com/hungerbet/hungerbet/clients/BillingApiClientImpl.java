package com.hungerbet.hungerbet.clients;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BillingApiClientImpl implements BillingApiClient {
    @Data
    @AllArgsConstructor
    public class DebitingMoneyRequest {
        private String login;
        private double amount;
    }

    @Value("${billing-api.path}")
    private String billingApiPath;

    @Override
    public ResponseEntity debitingMoney(String login, double amount) {
        RestTemplate template = new RestTemplate();

        DebitingMoneyRequest request = new DebitingMoneyRequest(login, amount);
        return template.postForEntity(billingApiPath + "/billing-api", request, ResponseEntity.class);
    }
}
