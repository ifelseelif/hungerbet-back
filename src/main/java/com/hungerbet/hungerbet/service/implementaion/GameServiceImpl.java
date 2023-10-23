package com.hungerbet.hungerbet.service.implementaion;

import com.hungerbet.hungerbet.controllers.models.events.EventBodyResponse;
import com.hungerbet.hungerbet.controllers.models.events.EventResponse;
import com.hungerbet.hungerbet.controllers.models.game.CreatePlannedEvents;
import com.hungerbet.hungerbet.controllers.models.game.GameResponse;
import com.hungerbet.hungerbet.entity.domain.*;
import com.hungerbet.hungerbet.entity.exceptions.HttpException;
import com.hungerbet.hungerbet.repository.*;
import com.hungerbet.hungerbet.service.GameService;
import com.hungerbet.hungerbet.service.PlayerService;
import com.hungerbet.hungerbet.service.PlannedEventsService;
import com.hungerbet.hungerbet.controllers.models.game.CreateGameModel;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private HappenedEventsRepository happenedEventsRepository;

    @Autowired
    private PlannedEventsService plannedEventsService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlannedEventRepository plannedEventRepository;

    @Override
    @Transactional
    public Game create(String managerLogin, CreateGameModel createGameModel) throws HttpException {
        User manager = userRepository.findByLogin(managerLogin).orElseThrow(() -> new HttpException("User not found", HttpStatus.NOT_FOUND));

        Game game = new Game(createGameModel.getName(), GameStatus.draft, createGameModel.getDateStart(), createGameModel.getArenaDescription(), createGameModel.getDescription(), createGameModel.getArenaType(), manager);
        Game savedGame = gameRepository.save(game);

        for (UUID playerId : createGameModel.getPlayers()) {
            playerService.attach(savedGame, playerId);
        }

        for (CreatePlannedEvents createPlannedEvents : createGameModel.getPlannedEvents()) {
            plannedEventsService.addPlannedEvent(savedGame, createPlannedEvents);
        }

        gameRepository.save(savedGame);
        return game;
    }

    @Override
    public GameResponse publishGame(UUID gameId) throws HttpException {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new HttpException("Game not found", HttpStatus.NOT_FOUND));

        game.publish();
        gameRepository.save(game);
        return new GameResponse(game);
    }

    @Override
    public Game getGame(UUID gameId, boolean isManager) throws HttpException {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new HttpException("Game not found", HttpStatus.NOT_FOUND));

        if (game.getStatus() == GameStatus.draft && !isManager) {
            throw new HttpException("Game is in draft status", HttpStatus.FORBIDDEN);
        }

        if (!isManager) {
            game.setHappenedEvents(null);
        }

        return game;
    }

    @Override
    public List<Game> getGames(boolean isManager) {
        List<Game> games = gameRepository.findAll();

        if (!isManager) {
            return games.stream().filter(game -> game.getStatus() != GameStatus.draft).toList();
        }

        return games;
    }

    @Override
    public GameResponse startGame(UUID gameId) throws HttpException {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new HttpException("Game not found", HttpStatus.NOT_FOUND));

        game.start();

        Date startDate = new Date();
        EventBody startGameBody = EventBody.CreateOtherEvent(null, "Игра началась");
        HappenedEvent startGameEvent = new HappenedEvent(startDate, HappenedEventType.other, startGameBody);
        happenedEventsRepository.save(startGameEvent);
        game.addHappenedEvent(startGameEvent);

        if (game.IsFinish()) {
            Date endDate = new Date();
            EventBody endGameBody = EventBody.CreateOtherEvent(null, "Игра закончилась");
            HappenedEvent endEvent = new HappenedEvent(endDate, HappenedEventType.other, endGameBody);
            happenedEventsRepository.save(endEvent);
            game.addHappenedEvent(endEvent);
            game.Finish();
        }

        gameRepository.save(game);
        return new GameResponse(game);
    }


    public List<EventResponse> getHappenedEvents(UUID gameId, boolean isManager) throws HttpException {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new HttpException("Game not found", HttpStatus.NOT_FOUND));

        if (game.getStatus() == GameStatus.draft && !isManager) {
            return new ArrayList<>();
        }

        return game.getHappenedEvents().stream().map(event -> {
                    switch (event.getHappenedEventType()) {
                        case other -> {
                            if (event.getBody().getPlayerId() != null) {
                                Player player = playerRepository.findById(event.getBody().getPlayerId()).orElse(new Player());
                                return new EventResponse(event.getId(), event.getHappenedEventType(), EventBodyResponse.CreateOtherEvent(player, event.getBody().getText()), event.getHappenedTime());
                            }
                            return new EventResponse(event.getId(), event.getHappenedEventType(), EventBodyResponse.CreateOtherEvent(null, event.getBody().getText()), event.getHappenedTime());
                        }
                        case random -> {
                            PlannedEvent plannedEvent = plannedEventRepository.findById(event.getBody().getPlannedEventId()).orElse(new PlannedEvent(""));
                            return new EventResponse(event.getId(), event.getHappenedEventType(), EventBodyResponse.CreateRandomEvent(plannedEvent.getName()), event.getHappenedTime());
                        }
                        case player_killed -> {
                            Player player = playerRepository.findById(event.getBody().getPlayerId()).orElse(new Player());
                            return new EventResponse(event.getId(), event.getHappenedEventType(), EventBodyResponse.CreatePlayerKillEvent(player), event.getHappenedTime());
                        }
                        case player_injured -> {
                            Player player = playerRepository.findById(event.getBody().getPlayerId()).orElse(new Player());
                            return new EventResponse(event.getId(), event.getHappenedEventType(), EventBodyResponse.CreatePlayerInjuryEvent(player, event.getBody().getPlayerState()), event.getHappenedTime());
                        }
                        case supply -> {
                            Player player = playerRepository.findById(event.getBody().getPlayerId()).orElse(new Player());
                            return new EventResponse(event.getId(), event.getHappenedEventType(), EventBodyResponse.CreateSupplyEvent(player, event.getBody().getItem()), event.getHappenedTime());
                        }
                        default -> {
                            System.out.println("Can't parse");
                            return new EventResponse();
                        }
                    }
                })
                .sorted(Comparator.comparing(EventResponse::getTime).reversed()).toList();
    }
}
