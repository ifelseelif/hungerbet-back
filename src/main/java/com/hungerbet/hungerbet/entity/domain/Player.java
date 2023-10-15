package com.hungerbet.hungerbet.entity.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Player {
    @Id
    @GeneratedValue
    private UUID id;

    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    @Enumerated(EnumType.STRING)
    private PlayerState state;

    public Player(String firstName, String lastName, int age, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        state = PlayerState.ALIVE;
    }

    public boolean isAlive() {
        return state != PlayerState.DEAD;
    }

    public void hurts() {
        this.state = PlayerState.FLESH_WOUND;
    }

    public void killed() {
        this.state = PlayerState.DEAD;
    }
}
