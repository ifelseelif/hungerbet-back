package com.hungerbet.hungerbet.controllers.models.game;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePlannedEvents {
    private String name;
    private String description;
    private Date dateStart;
}
