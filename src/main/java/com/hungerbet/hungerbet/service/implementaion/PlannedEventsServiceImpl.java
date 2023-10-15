package com.hungerbet.hungerbet.service.implementaion;

import com.hungerbet.hungerbet.controllers.models.game.CreatePlannedEvents;
import com.hungerbet.hungerbet.entity.domain.Game;
import com.hungerbet.hungerbet.entity.domain.PlannedEvent;
import com.hungerbet.hungerbet.entity.exceptions.HttpException;
import com.hungerbet.hungerbet.repository.GameRepository;
import com.hungerbet.hungerbet.repository.PlannedEventRepository;
import com.hungerbet.hungerbet.service.PlannedEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public Game addPlannedEvent(Game game, CreatePlannedEvents request) throws HttpException {
        PlannedEvent plannedEvent = new PlannedEvent(game.getId(), request.getDescription(), request.getName(), request.getDateStart());
        plannedEventRepository.save(plannedEvent);
        game.addPlannedEvent(plannedEvent);
        return game;
    }

    @Override
    public List<PlannedEvent> getPlannedEvents(UUID gameId) throws HttpException {
        Optional<Game> game = gameRepository.findById(gameId);
        game.orElseThrow(() -> new HttpException("Game not found", HttpStatus.NOT_FOUND));

        return game.get().getPlannedEvents();
    }
}
