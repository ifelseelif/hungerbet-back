package com.hungerbet.hungerbet.controllers;

import com.hungerbet.hungerbet.controllers.models.events.EventRequest;
import com.hungerbet.hungerbet.entity.domain.HappenedEvent;
import com.hungerbet.hungerbet.entity.exceptions.HttpException;
import com.hungerbet.hungerbet.service.GameEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game-events")
@CrossOrigin(origins = "*")
public class GameEventController {
    @Autowired
    GameEventService gameEventService;

    @Value("${static-api-key}")
    private String apiToken;

    @PostMapping
    public HappenedEvent addEvent(@RequestHeader String token, @RequestBody EventRequest event) throws HttpException {
        if (token.equals(apiToken)) {
            return gameEventService.AddEvent(event);
        }

        throw new HttpException("Api key wrong", HttpStatus.UNAUTHORIZED);
    }
}
