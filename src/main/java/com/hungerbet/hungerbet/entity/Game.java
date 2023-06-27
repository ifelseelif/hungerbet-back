package com.hungerbet.hungerbet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Date;
import java.util.UUID;

@Entity
public class Game {
    @Id
    @GeneratedValue
    private UUID id;

    private Date startDate;
    private Date endDate;
    private String arenaInfo;
    private String description;


}
