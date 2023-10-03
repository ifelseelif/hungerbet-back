package com.hungerbet.hungerbet.service;

import com.hungerbet.hungerbet.controllers.models.plannedEvents.CreatePlannedEventRequest;
import com.hungerbet.hungerbet.entity.domain.PlannedEvent;
import com.hungerbet.hungerbet.entity.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface PlannedEventsService {
    PlannedEvent addPlannedEvent(CreatePlannedEventRequest request) throws NotFoundException;
    List<PlannedEvent> getPlannedEvents(UUID gameId) throws NotFoundException;
}
