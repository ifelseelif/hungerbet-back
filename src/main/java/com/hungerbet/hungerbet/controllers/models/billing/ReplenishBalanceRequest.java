package com.hungerbet.hungerbet.controllers.models.billing;

import lombok.Data;

import java.util.UUID;

@Data
public class ReplenishBalanceRequest {
    private UUID userId;
    private double amount;
}
