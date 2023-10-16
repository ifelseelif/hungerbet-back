package com.hungerbet.hungerbet.schedulers;

import com.hungerbet.hungerbet.entity.domain.PlannedEvent;
import com.hungerbet.hungerbet.repository.PlannedEventRepository;
import com.hungerbet.hungerbet.service.GameEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Component
@EnableScheduling
public class Scheduler {
    @Autowired
    private PlannedEventRepository plannedEventRepository;

    @Value("${game-api}")
    private String apiPath;

    @Scheduled(cron = "1 * * * * *")
    public void runPlannedEvents() {
        System.out.println("RUN SCHEDULER");
        List<PlannedEvent> plannedEventList = plannedEventRepository.findAll();
        Date currentDate = new Date();
        List<PlannedEvent> plannedEvents = plannedEventList.stream().filter(event -> !event.isHappened() && event.getDateStart().before(currentDate)).toList();
        RestTemplate restTemplate = new RestTemplate();
        for (PlannedEvent plannedEvent : plannedEvents) {
            try {
                restTemplate.postForObject(apiPath, plannedEvent, void.class);
            } catch (Exception ignored) {
            }
        }
        System.out.println("END SCHEDULER");
    }
}
