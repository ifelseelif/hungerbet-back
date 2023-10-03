package com.hungerbet.hungerbet.service;

import com.hungerbet.hungerbet.controllers.models.participant.CreateParticipantRequest;
import com.hungerbet.hungerbet.entity.domain.Game;
import com.hungerbet.hungerbet.entity.domain.Participant;
import com.hungerbet.hungerbet.entity.exceptions.BadRequestException;
import com.hungerbet.hungerbet.entity.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface ParticipantService {
    Participant addParticipant(CreateParticipantRequest request) throws BadRequestException;

    //TODO update participant
    List<Participant> getAllParticipants();
    Game attachParticipant(UUID gameId, UUID participant) throws NotFoundException, BadRequestException;

}
