package com.hungerbet.hungerbet.controllers.models.game;

import com.hungerbet.hungerbet.entity.domain.GameStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class ChangeGameStatusRequest {
    private UUID gameId;
    private GameStatus newGameStatus;
}
