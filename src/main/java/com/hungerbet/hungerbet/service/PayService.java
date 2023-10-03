package com.hungerbet.hungerbet.service;

import com.hungerbet.hungerbet.controllers.models.billing.ReplenishBalanceRequest;
import com.hungerbet.hungerbet.entity.exceptions.BadRequestException;
import com.hungerbet.hungerbet.entity.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

public interface PayService {

    void ChangeBalance(ReplenishBalanceRequest request) throws BadRequestException, NotFoundException;
}
