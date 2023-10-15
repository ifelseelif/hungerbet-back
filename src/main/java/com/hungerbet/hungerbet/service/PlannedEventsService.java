package com.hungerbet.hungerbet.service;

import com.hungerbet.hungerbet.controllers.models.game.CreatePlannedEvents;
import com.hungerbet.hungerbet.entity.domain.Game;
import com.hungerbet.hungerbet.entity.domain.PlannedEvent;
import com.hungerbet.hungerbet.entity.exceptions.HttpException;

import java.util.List;
import java.util.UUID;

public interface PlannedEventsService {
    Game addPlannedEvent(Game game, CreatePlannedEvents request) throws HttpException;
    List<PlannedEvent> getPlannedEvents(UUID gameId) throws HttpException;
}
