package com.hungerbet.hungerbet.service;

import com.hungerbet.hungerbet.controllers.models.gameEvents.GameEventRequest;
import com.hungerbet.hungerbet.entity.domain.HappenedEvent;
import com.hungerbet.hungerbet.entity.exceptions.BadRequestException;
import com.hungerbet.hungerbet.entity.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface GameEventService {
    HappenedEvent AddEvent(GameEventRequest request) throws NotFoundException, BadRequestException;
    List<HappenedEvent> GetGameEvents(UUID gameId) throws NotFoundException;
}
