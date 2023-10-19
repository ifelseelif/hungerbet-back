package com.hungerbet.hungerbet.controllers.models.events;

import com.hungerbet.hungerbet.entity.domain.HappenedEventType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class EventResponse {
    private UUID id;
    private String type;
    private EventBodyResponse body;
    private Date happenedTime;

    public EventResponse(UUID id, HappenedEventType happenedEventType, EventBodyResponse eventBodyResponse, Date happenedTime) {
        this.id = id;
        this.type = happenedEventType.toString();
        this.body = eventBodyResponse;
        this.happenedTime = happenedTime;
    }
}
