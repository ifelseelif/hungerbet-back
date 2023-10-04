package com.hungerbet.hungerbet.controllers;

import com.hungerbet.hungerbet.controllers.models.game.ChangeGameStatusRequest;
import com.hungerbet.hungerbet.entity.domain.Game;
import com.hungerbet.hungerbet.entity.exceptions.BadRequestException;
import com.hungerbet.hungerbet.entity.exceptions.NotFoundException;
import com.hungerbet.hungerbet.service.GameService;
import com.hungerbet.hungerbet.service.models.game.CreateGameModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/games")
@CrossOrigin(origins = "*")
public class GameController {
    @Autowired
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Manager')")
    public Game create(@RequestBody CreateGameModel createGameRequest) {
        return gameService.create(createGameRequest);
    }

    @PutMapping("/{gameId}")
    @PreAuthorize("hasAuthority('Manager')")
    public Game update(@PathVariable UUID gameId, @RequestBody CreateGameModel createGameRequest) throws NotFoundException {
        return gameService.update(gameId, createGameRequest);
    }

    //TODO update game

    @PostMapping("/{gameId}/publish")
    @PreAuthorize("hasAuthority('Manager')")
    public Game publishGame(@PathVariable UUID gameId) throws NotFoundException, BadRequestException {
        return gameService.publishGame(gameId);
    }

    @PostMapping("/{gameId}/start")
    @PreAuthorize("hasAuthority('Manager')")
    public Game sartGame(@PathVariable UUID gameId) throws NotFoundException, BadRequestException {
        return gameService.startGame(gameId);
    }

    @GetMapping("/{gameId}")
    @PreAuthorize("hasAuthority('Manager')")
    public Game GetGame(@PathVariable UUID gameId) throws NotFoundException {
        return gameService.getGame(gameId);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('Manager')")
    public List<Game> GetGames() {
        return gameService.getGames();
    }

    @GetMapping("/all-published")
    public List<Game> GetPublishedGames() {
        return gameService.getPublishedGames();
    }
}
