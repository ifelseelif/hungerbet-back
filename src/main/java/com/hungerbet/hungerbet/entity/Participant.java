package com.hungerbet.hungerbet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;
import java.util.UUID;

@Entity
public class Participant {
    @Id
    @GeneratedValue
    private UUID id;

    private String firstName;
    private String lastName;
    private Date birthDate;
    private ParticipantState participantState;

}
