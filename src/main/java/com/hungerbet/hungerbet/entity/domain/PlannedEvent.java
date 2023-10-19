package com.hungerbet.hungerbet.entity.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class PlannedEvent {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String description;
    private Date dateStart;
    private UUID gameId;
    private boolean isHappened;

    public PlannedEvent(UUID gameId, String description, String name, Date dateStart) {
        this.gameId = gameId;
        this.name = name;
        this.description = description;
        this.dateStart = dateStart;
        this.isHappened = false;
    }

    public PlannedEvent(String name) {
        this.name = name;
    }
}
