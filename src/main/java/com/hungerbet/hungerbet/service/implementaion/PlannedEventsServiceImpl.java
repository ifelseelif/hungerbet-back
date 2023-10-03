package com.hungerbet.hungerbet.service.implementaion;

import com.hungerbet.hungerbet.controllers.models.plannedEvents.CreatePlannedEventRequest;
import com.hungerbet.hungerbet.entity.domain.Game;
import com.hungerbet.hungerbet.entity.domain.PlannedEvent;
import com.hungerbet.hungerbet.entity.exceptions.NotFoundException;
import com.hungerbet.hungerbet.repository.GameRepository;
import com.hungerbet.hungerbet.repository.PlannedEventRepository;
import com.hungerbet.hungerbet.service.PlannedEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlannedEventsServiceImpl implements PlannedEventsService {
    @Autowired
    private PlannedEventRepository plannedEventRepository;

    @Autowired
    private GameRepository gameRepository;

    @Override
    public PlannedEvent addPlannedEvent(CreatePlannedEventRequest request) throws NotFoundException {
        Optional<Game> game = gameRepository.findById(request.getGameId());
        game.orElseThrow(() -> new NotFoundException("Game not found"));

        PlannedEvent plannedEvent = new PlannedEvent(request.getGameId(), request.getType(), request.getName(), request.getScheduleTime());
        plannedEventRepository.save(plannedEvent);
        game.get().addPlannedEvent(plannedEvent);
        gameRepository.save(game.get());
        return plannedEvent;
    }

    @Override
    public List<PlannedEvent> getPlannedEvents(UUID gameId) throws NotFoundException {
        Optional<Game> game = gameRepository.findById(gameId);
        game.orElseThrow(() -> new NotFoundException("Game not found"));

        return game.get().getPlannedEvents();
    }
}
