package com.hungerbet.hungerbet.controllers;

import com.hungerbet.hungerbet.controllers.models.gameEvents.GameEventRequest;
import com.hungerbet.hungerbet.entity.domain.HappenedEvent;
import com.hungerbet.hungerbet.entity.exceptions.BadRequestException;
import com.hungerbet.hungerbet.entity.exceptions.NotFoundException;
import com.hungerbet.hungerbet.service.GameEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/game-events")
public class GameEventController {
    @Autowired
    GameEventService gameEventService;

    @PostMapping
    public HappenedEvent addEvent(@RequestBody GameEventRequest event) throws NotFoundException, BadRequestException {
        return gameEventService.AddEvent(event);
    }

    @GetMapping("/{gameId}")
    @Operation(summary = "My endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    public List<HappenedEvent> getEvents(@RequestParam UUID gameId) throws NotFoundException {
        return gameEventService.GetGameEvents(gameId);
    }
}
