package com.hungerbet.hungerbet.service;

import com.hungerbet.hungerbet.controllers.models.player.CreatePlayerRequest;
import com.hungerbet.hungerbet.entity.domain.Game;
import com.hungerbet.hungerbet.entity.domain.Player;
import com.hungerbet.hungerbet.entity.exceptions.HttpException;

import java.util.List;
import java.util.UUID;

public interface PlayerService {
    Player addParticipant(CreatePlayerRequest request) throws HttpException;

    List<Player> getAllParticipants();

    void attach(Game game, UUID playerId) throws HttpException;
}
