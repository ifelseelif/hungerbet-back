package com.hungerbet.hungerbet.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Balance {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    private Customer customer;

    private double amount;
    @Enumerated(EnumType.STRING)
    private GameStatus status;
}
