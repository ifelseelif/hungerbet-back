package com.hungerbet.hungerbet.service.implementaion;

import com.hungerbet.hungerbet.controllers.models.events.EventRequest;
import com.hungerbet.hungerbet.entity.domain.*;
import com.hungerbet.hungerbet.entity.exceptions.HttpException;
import com.hungerbet.hungerbet.repository.GameRepository;
import com.hungerbet.hungerbet.repository.HappenedEventsRepository;
import com.hungerbet.hungerbet.repository.PlannedEventRepository;
import com.hungerbet.hungerbet.repository.PlayerRepository;
import com.hungerbet.hungerbet.service.GameEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class GameEventServiceImpl implements GameEventService {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlannedEventRepository plannedEventRepository;

    @Autowired
    private HappenedEventsRepository happenedEventsRepository;

    @Override
    public HappenedEvent AddEvent(EventRequest request) throws HttpException {
        Game game = gameRepository.findById(request.getGameId()).orElseThrow(() -> new HttpException("Game not found", HttpStatus.NOT_FOUND));

        HappenedEvent happenedEvent = new HappenedEvent(request.getHappenedTime(), HappenedEventType.valueOf(request.getHappenedEventType()), request.getBody());
        happenedEventsRepository.save(happenedEvent);

        game.addHappenedEvent(happenedEvent);
        gameRepository.save(game);

        if (game.IsFinish() && happenedEvent.getHappenedEventType() != HappenedEventType.other) {
            return null;
        }

        switch (happenedEvent.getHappenedEventType()) {
            case player_killed -> {
                Player player = FindPlayer(request.getBody().getPlayerId());
                player.changeState(request.getBody().getPlayerState());
                playerRepository.save(player);

                if (game.IsFinish()) {
                    FinishGame(game);
                }
            }
            case player_injured -> {
                Player player = FindPlayer(request.getBody().getPlayerId());
                player.changeState(request.getBody().getPlayerState());
                playerRepository.save(player);
            }
            case random -> {
                PlannedEvent plannedEvent = plannedEventRepository.findById(request.getBody().getPlannedEventId()).orElseThrow(() -> new HttpException("Not found", HttpStatus.NOT_FOUND));
                plannedEvent.setHappened(true);
                plannedEventRepository.save(plannedEvent);
            }
        }

        return happenedEvent;
    }


    private Player FindPlayer(UUID playerId) throws HttpException {
        return playerRepository.findById(playerId).orElseThrow(() -> new HttpException("Not found player", HttpStatus.NOT_FOUND));
    }

    private void FinishGame(Game game) {
        System.out.println("ENd of game");
        Date currentDate = new Date();
        EventBody eventBody = EventBody.CreateOtherEvent(null, "Игра закончилась");
        HappenedEvent endGameEvent = new HappenedEvent(currentDate, HappenedEventType.other, eventBody);
        happenedEventsRepository.save(endGameEvent);
        game.addHappenedEvent(endGameEvent);
        gameRepository.save(game);
    }
}
