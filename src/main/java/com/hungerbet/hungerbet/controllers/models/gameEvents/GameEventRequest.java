package com.hungerbet.hungerbet.controllers.models.gameEvents;

import com.hungerbet.hungerbet.entity.domain.HappenedEventType;
import com.hungerbet.hungerbet.entity.domain.Participant;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class GameEventRequest {
    private UUID gameId;
    private UUID participantId;
    private String name;
    private String description;
    private HappenedEventType happenedEventType;

    public GameEventRequest(UUID gameId, String name, String description, HappenedEventType happenedEventType) {
        this.gameId = gameId;
        this.name = name;
        this.description = description;
        this.happenedEventType = happenedEventType;
    }
}
