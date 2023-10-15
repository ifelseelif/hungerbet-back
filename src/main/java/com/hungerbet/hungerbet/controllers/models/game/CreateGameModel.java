package com.hungerbet.hungerbet.controllers.models.game;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class CreateGameModel {
    private String name;
    private String arenaType;
    private String arenaDescription;
    private Date dateStart;
    private String description;

    private List<UUID> players;
    private List<CreatePlannedEvents> plannedEvents;
    private List<UUID> items;
}
