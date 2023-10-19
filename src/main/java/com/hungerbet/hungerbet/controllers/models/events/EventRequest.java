package com.hungerbet.hungerbet.controllers.models.events;

import com.hungerbet.hungerbet.entity.domain.EventBody;
import com.hungerbet.hungerbet.entity.domain.HappenedEventType;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class EventRequest {
    private UUID gameId;
    private String happenedEventType;
    private Date happenedTime;
    private EventBody body;
}
