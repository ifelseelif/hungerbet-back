package com.hungerbet.hungerbet.service.models.game;

import lombok.Data;

import java.util.Date;

@Data
public class CreateGameModel {
    private String name;
    private Date startedAt;
    private String arenInfo;
    private String description;
    private boolean isQuarterlyMassacre;
    private String arena;
}
