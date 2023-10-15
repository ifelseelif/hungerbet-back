package com.hungerbet.hungerbet.controllers.models.player;

import lombok.Data;

@Data
public class CreatePlayerRequest {
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
}
