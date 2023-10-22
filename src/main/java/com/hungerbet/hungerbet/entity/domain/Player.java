package com.hungerbet.hungerbet.entity.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Player {
    public int district;
    @Id
    @GeneratedValue
    private UUID id;

    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    @Enumerated(EnumType.STRING)
    private PlayerState state;

    public Player(String firstName, String lastName, int age, String gender, int district) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        state = PlayerState.alive;
        this.district = district;
    }

    public boolean isDead() {
        return state == PlayerState.dead;
    }

    public void changeState(PlayerState newState) {
        this.state = newState;
    }
}
