package com.hungerbet.hungerbet.service;

import com.hungerbet.hungerbet.controllers.models.events.EventResponse;
import com.hungerbet.hungerbet.controllers.models.game.CreateGameModel;
import com.hungerbet.hungerbet.entity.domain.Game;
import com.hungerbet.hungerbet.entity.exceptions.HttpException;

import java.util.List;
import java.util.UUID;

public interface GameService {
    Game create(String managerLogin, CreateGameModel game) throws HttpException;

    void publishGame(UUID gameId) throws HttpException;

    Game getGame(UUID gameId, boolean isManager) throws HttpException;

    List<Game> getGames(boolean isManager);

    void startGame(UUID gameId) throws HttpException;

    List<EventResponse> getHappenedEvents(UUID gameId, boolean isManager) throws HttpException;
}
