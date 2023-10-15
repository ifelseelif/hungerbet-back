package com.hungerbet.hungerbet.controllers.models.events;

import com.hungerbet.hungerbet.entity.domain.HappenedEventType;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class EventRequest {
    private UUID gameId;
    private HappenedEventType happenedEventType;
    private Date happenedTime;
    private String body;

    public EventRequest(UUID gameId, HappenedEventType happenedEventType, String body) {
        this.gameId = gameId;
        this.body = body;
        this.happenedEventType = happenedEventType;
    }
}
