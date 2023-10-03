package com.hungerbet.hungerbet.service.implementaion;

import com.hungerbet.hungerbet.controllers.models.gameEvents.GameEventRequest;
import com.hungerbet.hungerbet.entity.domain.Game;
import com.hungerbet.hungerbet.entity.domain.HappenedEvent;
import com.hungerbet.hungerbet.entity.domain.HappenedEventType;
import com.hungerbet.hungerbet.entity.domain.Participant;
import com.hungerbet.hungerbet.entity.exceptions.BadRequestException;
import com.hungerbet.hungerbet.entity.exceptions.NotFoundException;
import com.hungerbet.hungerbet.repository.GameRepository;
import com.hungerbet.hungerbet.repository.HappenedEventsRepository;
import com.hungerbet.hungerbet.repository.ParticipantRepository;
import com.hungerbet.hungerbet.service.GameEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GameEventServiceImpl implements GameEventService {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private HappenedEventsRepository happenedEventsRepository;

    @Override
    public HappenedEvent AddEvent(GameEventRequest request) throws NotFoundException, BadRequestException {
        Optional<Game> game = gameRepository.findById(request.getGameId());
        game.orElseThrow(() -> new NotFoundException("Game not found"));

        HappenedEvent happenedEvent = new HappenedEvent(request.getName(), request.getDescription(), new Date(), request.getHappenedEventType());

        if (request.getHappenedEventType() != HappenedEventType.PLANNED_EVENT &&
                request.getHappenedEventType() != HappenedEventType.OTHERS) {
            Optional<Participant> participant = participantRepository.findById(request.getParticipantId());
            participant.orElseThrow(() -> new NotFoundException("Participant not found"));
            happenedEvent.setParticipantId(participant.get().getId());
        }

        happenedEventsRepository.save(happenedEvent);
        game.get().addHappenedEvent(happenedEvent);

        if (request.getHappenedEventType() != HappenedEventType.PLANNED_EVENT && game.get().IsFinish()) {
            System.out.println("ENd of game");
            Date currentDate = new Date();
            HappenedEvent endGameEvent = new HappenedEvent("Игра закончилась", "", currentDate, HappenedEventType.OTHERS);
            happenedEventsRepository.save(endGameEvent);
            game.get().addHappenedEvent(endGameEvent);
        }

        gameRepository.save(game.get());

        return happenedEvent;
    }

    @Override
    public List<HappenedEvent> GetGameEvents(UUID gameId) throws NotFoundException {
        Optional<Game> game = gameRepository.findById(gameId);
        game.orElseThrow(() -> new NotFoundException("Game not found"));

        return game.get().getHappenedEvents();
    }
}
