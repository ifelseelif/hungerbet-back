package com.hungerbet.hungerbet.service;

import com.hungerbet.hungerbet.controllers.models.game.CreatePlannedEvents;
import com.hungerbet.hungerbet.entity.domain.Game;
import com.hungerbet.hungerbet.entity.exceptions.HttpException;

public interface PlannedEventsService {
    Game addPlannedEvent(Game game, CreatePlannedEvents request) throws HttpException;
}
