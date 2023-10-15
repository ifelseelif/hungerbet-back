package com.hungerbet.hungerbet.controllers.models.player;

import com.hungerbet.hungerbet.entity.domain.Player;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class PlayerResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String state;

    public PlayerResponse(Player player){
        this.id = player.getId();
        this.firstName = player.getFirstName();
        this.lastName = player.getLastName();
        this.age = player.getAge();
        this.gender = player.getGender();
        this.state = player.getState().toString();
    }
}
