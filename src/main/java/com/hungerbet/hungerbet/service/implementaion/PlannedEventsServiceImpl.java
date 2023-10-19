package com.hungerbet.hungerbet.service.implementaion;

import com.hungerbet.hungerbet.controllers.models.game.CreatePlannedEvents;
import com.hungerbet.hungerbet.entity.domain.Game;
import com.hungerbet.hungerbet.entity.domain.PlannedEvent;
import com.hungerbet.hungerbet.entity.exceptions.HttpException;
import com.hungerbet.hungerbet.repository.PlannedEventRepository;
import com.hungerbet.hungerbet.service.PlannedEventsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlannedEventsServiceImpl implements PlannedEventsService {
    @Autowired
    private PlannedEventRepository plannedEventRepository;

    @Override
    public Game addPlannedEvent(Game game, CreatePlannedEvents request) throws HttpException {
        PlannedEvent plannedEvent = new PlannedEvent(game.getId(), request.getDescription(), request.getName(), request.getDateStart());
        plannedEventRepository.save(plannedEvent);
        game.addPlannedEvent(plannedEvent);
        return game;
    }

}