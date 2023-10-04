package com.hungerbet.hungerbet.controllers;

import com.hungerbet.hungerbet.controllers.models.participant.CreateParticipantRequest;
import com.hungerbet.hungerbet.entity.domain.Game;
import com.hungerbet.hungerbet.entity.domain.Participant;
import com.hungerbet.hungerbet.entity.exceptions.BadRequestException;
import com.hungerbet.hungerbet.entity.exceptions.NotFoundException;
import com.hungerbet.hungerbet.service.ParticipantService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/participants")
@CrossOrigin(origins = "*")
@PreAuthorize("hasAuthority('Manager')")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @PostMapping
    public Participant create(@RequestBody CreateParticipantRequest request) throws BadRequestException {
        return participantService.addParticipant(request);
    }

    @GetMapping("/all")
    public List<Participant> getAll() {
        return participantService.getAllParticipants();
    }

    @PostMapping("/{participantId}/{gameId}/attach")
    public Game attachParticipant(@PathVariable UUID participantId, @PathVariable UUID gameId) throws NotFoundException, BadRequestException {
        return participantService.attachParticipant(gameId, participantId);
    }
}
