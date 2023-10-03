package com.hungerbet.hungerbet.entity.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Participant {
    @Id
    @GeneratedValue
    private UUID id;

    private String firstName;
    private String lastName;
    private int age;
    private int gender;
    @Enumerated(EnumType.STRING)
    private ParticipantState state;

    public Participant(String firstName, String lastName, int age, int gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        state = ParticipantState.ALIVE;
    }

    public boolean isAlive() {
        return state != ParticipantState.DEAD;
    }
}
