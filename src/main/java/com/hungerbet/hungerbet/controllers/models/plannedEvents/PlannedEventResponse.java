package com.hungerbet.hungerbet.controllers.models.plannedEvents;

import com.hungerbet.hungerbet.entity.domain.PlannedEvent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class PlannedEventResponse {
    private UUID id;
    private String name;
    private String description;
    private Date dateStart;
    private UUID gameId;
    private boolean isHappened;

    public PlannedEventResponse(PlannedEvent plannedEvent) {
        this.gameId = plannedEvent.getGameId();
        this.name = plannedEvent.getName();
        this.description = plannedEvent.getDescription();
        this.dateStart = plannedEvent.getDateStart();
        this.isHappened = plannedEvent.isHappened();
    }
}
