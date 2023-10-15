package com.hungerbet.hungerbet.controllers.models.users;

import com.hungerbet.hungerbet.entity.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseModel {
    private UUID id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;

    public UserResponseModel(User user) {
        this.email = user.getEmail();
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.id = user.getId();
    }
}
