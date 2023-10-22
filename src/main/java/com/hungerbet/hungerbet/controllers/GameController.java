package com.hungerbet.hungerbet.controllers;

import com.hungerbet.hungerbet.controllers.models.events.EventResponse;
import com.hungerbet.hungerbet.controllers.models.game.CreateGameModel;
import com.hungerbet.hungerbet.controllers.models.game.GameResponse;
import com.hungerbet.hungerbet.entity.domain.Game;
import com.hungerbet.hungerbet.entity.domain.Role;
import com.hungerbet.hungerbet.entity.domain.User;
import com.hungerbet.hungerbet.entity.exceptions.HttpException;
import com.hungerbet.hungerbet.repository.UserRepository;
import com.hungerbet.hungerbet.service.GameService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
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
    private final UserRepository userRepository;

    @Autowired
    public GameController(GameService gameService, UserRepository userRepository) {
        this.gameService = gameService;
        this.userRepository = userRepository;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Manager')")
    public GameResponse create(@RequestBody CreateGameModel createGameRequest, Principal principal) throws HttpException {
        Game game = gameService.create(principal.getName(), createGameRequest);

        GameResponse gameResponse = new GameResponse(game);
        gameResponse.setHappenedEvents(gameService.getHappenedEvents(game.getId(), true));
        return gameResponse;
    }

    @PostMapping("/{gameId}/publish")
    @PreAuthorize("hasAuthority('Manager')")
    public GameResponse publishGame(@PathVariable UUID gameId) throws HttpException {
        return gameService.publishGame(gameId);
    }

    @PostMapping("/{gameId}/start")
    @PreAuthorize("hasAuthority('Manager')")
    public GameResponse startGame(@PathVariable UUID gameId) throws HttpException {
        return gameService.startGame(gameId);
    }

    @GetMapping("/{gameId}/events")
    public List<EventResponse> GetGameEvents(@PathVariable UUID gameId, Principal principal, @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date after) throws HttpException {
        if (after == null) {
            return gameService.getHappenedEvents(gameId, isManager(principal));
        }
        return gameService.getHappenedEvents(gameId, isManager(principal)).stream().filter(event -> event.getTime().after(after)).toList();
    }

    @GetMapping("/{gameId}")
    public GameResponse GetGame(@PathVariable UUID gameId, Principal principal) throws HttpException {
        GameResponse gameResponse = new GameResponse(gameService.getGame(gameId, isManager(principal)));

        if (isManager(principal)) {
            gameResponse.setHappenedEvents(gameService.getHappenedEvents(gameId, true));
        }

        return gameResponse;
    }

    @GetMapping()
    public List<GameResponse> GetGames(Principal principal) throws HttpException {
        List<GameResponse> gameResponses = gameService.getGames(isManager(principal)).stream().map(GameResponse::new).toList();

        if (isManager(principal)) {
            for (GameResponse gameResponse : gameResponses) {
                gameResponse.setHappenedEvents(gameService.getHappenedEvents(gameResponse.getId(), true));
            }
        }

        return gameResponses;
    }

    private boolean isManager(Principal principal) throws HttpException {
        String login = principal.getName();
        User user = userRepository.findByLogin(login).orElseThrow(() -> new HttpException("User not found", HttpStatus.BAD_REQUEST));
        return user.getRole() == Role.admin;
    }
}
