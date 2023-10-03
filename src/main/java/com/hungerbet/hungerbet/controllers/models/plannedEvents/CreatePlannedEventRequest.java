package com.hungerbet.hungerbet.controllers.models.plannedEvents;

import com.hungerbet.hungerbet.entity.domain.PlannedEventType;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class CreatePlannedEventRequest {
    private UUID gameId;
    private PlannedEventType type;
    private String name;
    private Date scheduleTime;
}
