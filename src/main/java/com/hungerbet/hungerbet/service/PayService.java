package com.hungerbet.hungerbet.service;

import com.hungerbet.hungerbet.entity.exceptions.HttpException;

public interface PayService {

    double ChangeBalance(String login, double amount) throws HttpException;
}
