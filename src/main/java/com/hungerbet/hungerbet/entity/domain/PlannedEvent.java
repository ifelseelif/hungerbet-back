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
    @Enumerated(EnumType.STRING)
    private PlannedEventType type;
    private String name;
    private Date scheduleTime;
    private UUID gameId;
    private boolean isHappened;

    public PlannedEvent(UUID gameId, PlannedEventType type, String name, Date scheduleTime) {
        this.gameId = gameId;
        this.type = type;
        this.name = name;
        this.scheduleTime = scheduleTime;
        this.isHappened = false;
    }
}
