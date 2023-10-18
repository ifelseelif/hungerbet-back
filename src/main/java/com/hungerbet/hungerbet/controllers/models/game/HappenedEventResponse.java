package com.hungerbet.hungerbet.controllers.models.game;

import com.hungerbet.hungerbet.entity.domain.HappenedEvent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class HappenedEventResponse {
    private UUID id;
    private Date time;
    private String happenedEventType;
    private String body;

    public HappenedEventResponse(HappenedEvent happenedEvent) {
        this.time = happenedEvent.getHappenedTime();
        this.id = happenedEvent.getId();
        this.happenedEventType = happenedEvent.getHappenedEventType().toString();
        this.body = happenedEvent.getBody();
    }
}
