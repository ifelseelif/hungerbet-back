package com.hungerbet.hungerbet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Customer {
    @Id
    @GeneratedValue
    private UUID id;

    private String firstName;
    private String secondName;

}
