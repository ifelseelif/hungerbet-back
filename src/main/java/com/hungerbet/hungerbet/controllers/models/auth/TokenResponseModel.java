package com.hungerbet.hungerbet.controllers.models.auth;

import com.hungerbet.hungerbet.controllers.models.users.UserResponseModel;
import com.hungerbet.hungerbet.entity.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenResponseModel {
    private String token;
    private UserResponseModel user;

    public TokenResponseModel(String token, User user) {
        this.token = token;
        this.user = new UserResponseModel(user);
    }
}
