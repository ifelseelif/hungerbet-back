package com.hungerbet.hungerbet.controllers;

import com.hungerbet.hungerbet.controllers.models.plannedEvents.CreatePlannedEventRequest;
import com.hungerbet.hungerbet.entity.domain.PlannedEvent;
import com.hungerbet.hungerbet.entity.exceptions.NotFoundException;
import com.hungerbet.hungerbet.service.PlannedEventsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/planned-game-events")
@SecurityRequirement(name = "bearerAuth")
@CrossOrigin(origins = "*")
@PreAuthorize("hasAuthority('Manager')")
public class PlannedEventController {

    @Autowired
    private PlannedEventsService plannedEventsService;

    @PostMapping
    public PlannedEvent create(@RequestBody CreatePlannedEventRequest request) throws NotFoundException {
        return plannedEventsService.addPlannedEvent(request);
    }

    @GetMapping("/{gameId}")
    public List<PlannedEvent> getAll(@PathVariable UUID gameId) throws NotFoundException {
        return plannedEventsService.getPlannedEvents(gameId);
    }
}
