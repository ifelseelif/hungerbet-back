package com.hungerbet.hungerbet.service.implementaion;

import com.hungerbet.hungerbet.clients.BillingApiClient;
import com.hungerbet.hungerbet.controllers.models.billing.ReplenishBalanceRequest;
import com.hungerbet.hungerbet.entity.domain.User;
import com.hungerbet.hungerbet.entity.exceptions.BadRequestException;
import com.hungerbet.hungerbet.entity.exceptions.NotFoundException;
import com.hungerbet.hungerbet.repository.UserRepository;
import com.hungerbet.hungerbet.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BillingApiClient billingApiClient;

    @Override
    public void ChangeBalance(ReplenishBalanceRequest request) throws BadRequestException, NotFoundException {
        if (request.getAmount() < 0) {
            throw new BadRequestException("amount less than zero");
        }

        Optional<User> userOptional = userRepository.findById(request.getUserId());

        if (!userOptional.isPresent()) {
            throw new NotFoundException("User not found");
        }

        try {
            billingApiClient.debitingMoney(userOptional.get().getLogin(), request.getAmount());
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 400) {
                throw new BadRequestException("Not enough money");
            }
            throw new BadRequestException("Billing api not available, try late");
        } catch (Exception e) {
            throw new BadRequestException("Billing api not available, try late");
        }

        User user = userOptional.get();
        user.addMoney(request.getAmount());
        userRepository.save(user);
        System.out.println("ReplenishBalanceRequest success");

    }
}
