package com.hungerbet.hungerbet.service.implementaion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hungerbet.hungerbet.controllers.models.events.EventRequest;
import com.hungerbet.hungerbet.entity.domain.Game;
import com.hungerbet.hungerbet.entity.domain.HappenedEvent;
import com.hungerbet.hungerbet.entity.domain.HappenedEventType;
import com.hungerbet.hungerbet.entity.domain.Player;
import com.hungerbet.hungerbet.entity.domain.events.PlayerEvent;
import com.hungerbet.hungerbet.entity.domain.events.SupplyEvent;
import com.hungerbet.hungerbet.entity.exceptions.HttpException;
import com.hungerbet.hungerbet.repository.GameRepository;
import com.hungerbet.hungerbet.repository.HappenedEventsRepository;
import com.hungerbet.hungerbet.repository.PlayerRepository;
import com.hungerbet.hungerbet.service.GameEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GameEventServiceImpl implements GameEventService {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private HappenedEventsRepository happenedEventsRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public HappenedEvent AddEvent(EventRequest request) throws HttpException {
        Game game = gameRepository.findById(request.getGameId()).orElseThrow(() -> new HttpException("Game not found", HttpStatus.NOT_FOUND));

        HappenedEvent happenedEvent = new HappenedEvent(request.getHappenedTime(), request.getHappenedEventType(), request.getBody());
        happenedEventsRepository.save(happenedEvent);

        game.addHappenedEvent(happenedEvent);
        gameRepository.save(game);

        if (game.IsFinish() && happenedEvent.getHappenedEventType() != HappenedEventType.OTHER_EVENT) {
            return null;
        }

        switch (happenedEvent.getHappenedEventType()) {
            case HURTS_EVENT -> {
                Player player = FindPlayer(game, happenedEvent.getBody());
                player.hurts();
                playerRepository.save(player);
            }
            case KILLED_EVENT -> {
                Player player = FindPlayer(game, happenedEvent.getBody());
                player.killed();
                playerRepository.save(player);

                if (game.IsFinish()) {
                    FinishGame(game);
                }

            }
            case SUPPLY_EVENT -> {
                String itemName;
                try {
                    itemName = mapper.readValue(happenedEvent.getBody(), SupplyEvent.class).getItemName();
                } catch (JsonProcessingException e) {
                    throw new HttpException("Bad event structure", HttpStatus.BAD_REQUEST);
                }
                if (!game.getItems().stream().anyMatch(item -> item.getName().equals(itemName))) {
                    throw new HttpException("Item not allowed in this game", HttpStatus.BAD_REQUEST);
                }
            }
        }

        return happenedEvent;
    }


    private Player FindPlayer(Game game, String body) throws HttpException {
        PlayerEvent event;
        try {
            event = mapper.readValue(body, PlayerEvent.class);
        } catch (JsonProcessingException e) {
            throw new HttpException("Bad event structure", HttpStatus.BAD_REQUEST);
        }

        return playerRepository.findById(event.getPlayerId()).orElseThrow(() -> new HttpException("Not found player", HttpStatus.NOT_FOUND));
    }

    private void FinishGame(Game game) throws HttpException {
        System.out.println("ENd of game");
        Date currentDate = new Date();
        HappenedEvent endGameEvent = new HappenedEvent(currentDate, HappenedEventType.OTHER_EVENT, "{\"message\" : \"Игра закончилась\"}");
        happenedEventsRepository.save(endGameEvent);
        game.addHappenedEvent(endGameEvent);
        gameRepository.save(game);
    }
}
