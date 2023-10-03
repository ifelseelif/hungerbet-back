package com.hungerbet.hungerbet.service;

import com.hungerbet.hungerbet.entity.domain.Game;
import com.hungerbet.hungerbet.entity.domain.GameStatus;
import com.hungerbet.hungerbet.entity.exceptions.BadRequestException;
import com.hungerbet.hungerbet.entity.exceptions.NotFoundException;
import com.hungerbet.hungerbet.service.models.game.CreateGameModel;

import java.util.List;
import java.util.UUID;

public interface GameService {
    Game create(CreateGameModel game);
    Game publishGame(UUID gameId) throws NotFoundException, BadRequestException;

    Game getGame(UUID gameId) throws NotFoundException;

    List<Game> getGames();

    List<Game> getPublishedGames();

    Game startGame(UUID gameId) throws NotFoundException, BadRequestException;
}
