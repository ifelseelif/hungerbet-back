package com.hungerbet.hungerbet.schedulers;

import com.hungerbet.hungerbet.repository.PlannedEventRepository;
import com.hungerbet.hungerbet.service.GameEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
    public void runPlannedEvents() {
        //TODO fix it, add new service
/*        System.out.println("RUN SCHEDULER");
        List<PlannedEvent> plannedEventList = plannedEventRepository.findAll();
        Date currentDate = new Date();
        List<PlannedEvent> plannedEvents = plannedEventList.stream().filter(event -> !event.isHappened() && event.getDateStart().before(currentDate)).toList();
        for (PlannedEvent plannedEvent : plannedEvents) {
            try {
                gameEventService.AddEvent(new EventRequest(plannedEvent.getGameId(), plannedEvent.getName(), HappenedEventType.RANDOM_EVENT));
            } catch (Exception ignored) {
                System.out.println(ignored.getMessage());
            }
            plannedEvent.setHappened(true);
            plannedEventRepository.save(plannedEvent);
        }
        System.out.println("END SCHEDULER");*/
    }
}
