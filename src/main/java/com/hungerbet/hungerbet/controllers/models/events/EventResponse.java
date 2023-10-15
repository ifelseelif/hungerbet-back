package com.hungerbet.hungerbet.controllers.models.events;

import com.hungerbet.hungerbet.entity.domain.HappenedEvent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class EventResponse {
    private UUID id;
    private String type;
    private String body;
    private Date happenedTime;

    public EventResponse(HappenedEvent happenedEvent) {
        this.id = happenedEvent.getId();
        this.type = happenedEvent.getHappenedEventType().toString();
        this.body = happenedEvent.getBody();
        this.happenedTime = happenedEvent.getHappenedTime();
    }
}
