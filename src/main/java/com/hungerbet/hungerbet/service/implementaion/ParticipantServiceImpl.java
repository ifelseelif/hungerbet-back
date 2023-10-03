package com.hungerbet.hungerbet.service.implementaion;

import com.hungerbet.hungerbet.controllers.models.participant.CreateParticipantRequest;
import com.hungerbet.hungerbet.entity.domain.Game;
import com.hungerbet.hungerbet.entity.domain.Participant;
import com.hungerbet.hungerbet.entity.exceptions.BadRequestException;
import com.hungerbet.hungerbet.entity.exceptions.NotFoundException;
import com.hungerbet.hungerbet.repository.GameRepository;
import com.hungerbet.hungerbet.repository.ParticipantRepository;
import com.hungerbet.hungerbet.service.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private GameRepository gameRepository;

    @Override
    public Participant addParticipant(CreateParticipantRequest request) throws BadRequestException {
        Optional<Participant> participant = participantRepository.findParticipant(request.getFirstName(), request.getLastName(), request.getAge(), request.getGender());
        if(participant.isPresent()){
            throw new BadRequestException("Participant already created");
        }

        Participant newParticipant = new Participant(request.getFirstName(), request.getLastName(), request.getAge(), request.getGender());
        participantRepository.save(newParticipant);

        return newParticipant;
    }

    @Override
    public List<Participant> getAllParticipants() {
        return participantRepository.findAll();
    }

    @Override
    public Game attachParticipant(UUID gameId, UUID participantId) throws NotFoundException, BadRequestException {
        Optional<Game> game = gameRepository.findById(gameId);
        game.orElseThrow(()-> new NotFoundException("Game not found"));

        Optional<Participant> participant = participantRepository.findById(participantId);
        participant.orElseThrow(()-> new NotFoundException("Participant not found"));
        game.get().addParticipant(participant.get());
        gameRepository.save(game.get());

        return game.get();
    }
}
