package com.hungerbet.hungerbet.schedulers;

import com.hungerbet.hungerbet.controllers.models.gameEvents.GameEventRequest;
import com.hungerbet.hungerbet.entity.domain.HappenedEventType;
import com.hungerbet.hungerbet.entity.domain.PlannedEvent;
import com.hungerbet.hungerbet.entity.exceptions.BadRequestException;
import com.hungerbet.hungerbet.entity.exceptions.NotFoundException;
import com.hungerbet.hungerbet.repository.PlannedEventRepository;
import com.hungerbet.hungerbet.service.GameEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class Scheduler {
    private PlannedEventRepository plannedEventRepository;

    private GameEventService gameEventService;

    @Autowired
    public Scheduler(PlannedEventRepository plannedEventRepository, GameEventService gameEventService) {
        this.plannedEventRepository = plannedEventRepository;
        this.gameEventService = gameEventService;
    }

    @Scheduled(cron = "1 * * * * *")
    public void runPlannedEvents() throws NotFoundException, BadRequestException {
        System.out.println("RUN SCHEDULER");
        List<PlannedEvent> plannedEventList = plannedEventRepository.findAll();
        Date currentDate = new Date();
        List<PlannedEvent> plannedEvents = plannedEventList.stream().filter(event -> !event.isHappened() && event.getScheduleTime().before(currentDate)).toList();
        for (PlannedEvent plannedEvent: plannedEvents) {
            try {
                gameEventService.AddEvent(new GameEventRequest(plannedEvent.getGameId(), plannedEvent.getName(), plannedEvent.getType().toString(), HappenedEventType.PLANNED_EVENT));
            } catch (Exception ignored) {
                System.out.println(ignored.getMessage());
            }
            plannedEvent.setHappened(true);
            plannedEventRepository.save(plannedEvent);
        }
        System.out.println("END SCHEDULER");
    }
}
