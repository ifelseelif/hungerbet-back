package com.hungerbet.hungerbet.service.implementaion;

import com.hungerbet.hungerbet.clients.BillingApiClient;
import com.hungerbet.hungerbet.entity.domain.User;
import com.hungerbet.hungerbet.entity.exceptions.HttpException;
import com.hungerbet.hungerbet.repository.UserRepository;
import com.hungerbet.hungerbet.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BillingApiClient billingApiClient;

    @Override
    public double ChangeBalance(String login, double amount) throws HttpException {
        if (amount < 0) {
            throw new HttpException("amount less than zero", HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByLogin(login).orElseThrow(() -> new HttpException("User not found", HttpStatus.NOT_FOUND));

        try {
            billingApiClient.debitingMoney(login, amount);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 400) {
                throw new HttpException("Not enough money", HttpStatus.BAD_REQUEST);
            }
            throw new HttpException("Billing api not available, try late", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new HttpException("Billing api not available, try late", HttpStatus.BAD_REQUEST);
        }

        user.addMoney(amount);
        userRepository.save(user);
        return user.getBalanceMoney();
    }
}
