package com.hungerbet.hungerbet.controllers.models.users;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String firstName;
    private String secondName;
    private String login;
    private String password;
}
