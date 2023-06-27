package com.hungerbet.hungerbet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.util.UUID;

@Entity
public class Customer {
    @Id
    @GeneratedValue
    private UUID id;

    private String firstName;
    private String secondName;

    @OneToOne
    private Balance balance;

    private String login;
    private String passwd;

}
