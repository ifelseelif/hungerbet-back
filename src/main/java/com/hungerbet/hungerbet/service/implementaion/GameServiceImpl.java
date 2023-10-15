package com.hungerbet.hungerbet.service.implementaion;

import com.hungerbet.hungerbet.controllers.models.game.CreatePlannedEvents;
import com.hungerbet.hungerbet.entity.domain.*;
import com.hungerbet.hungerbet.entity.exceptions.HttpException;
import com.hungerbet.hungerbet.repository.GameRepository;
import com.hungerbet.hungerbet.repository.HappenedEventsRepository;
import com.hungerbet.hungerbet.repository.ItemRepository;
import com.hungerbet.hungerbet.repository.UserRepository;
import com.hungerbet.hungerbet.service.GameService;
import com.hungerbet.hungerbet.service.PlayerService;
import com.hungerbet.hungerbet.service.PlannedEventsService;
import com.hungerbet.hungerbet.controllers.models.game.CreateGameModel;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @Override
    @Transactional
    public Game create(String managerLogin, CreateGameModel createGameModel) throws HttpException {
        User manager = userRepository.findByLogin(managerLogin).orElseThrow(() -> new HttpException("User not found", HttpStatus.NOT_FOUND));

        Game game = new Game(createGameModel.getName(), GameStatus.DRAFT, createGameModel.getDateStart(), createGameModel.getArenaDescription(), createGameModel.getDescription(), createGameModel.getArenaType(), manager);
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
    public void publishGame(UUID gameId) throws HttpException {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new HttpException("Game not found", HttpStatus.NOT_FOUND));

        game.publish();
        gameRepository.save(game);
    }

    @Override
    public Game getGame(UUID gameId, boolean isManager) throws HttpException {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new HttpException("Game not found", HttpStatus.NOT_FOUND));

        if (game.getStatus() == GameStatus.DRAFT && !isManager) {
            throw new HttpException("Game is in draft status", HttpStatus.FORBIDDEN);
        }

        return game;
    }

    @Override
    public List<Game> getGames(boolean isManager) {
        List<Game> games = gameRepository.findAll();

        if (!isManager) {
            return games.stream().filter(game -> game.getStatus() != GameStatus.DRAFT).toList();
        }

        return games;
    }

    @Override
    public void startGame(UUID gameId) throws HttpException {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new HttpException("Game not found", HttpStatus.NOT_FOUND));

        game.start();

        Date startDate = new Date();
        HappenedEvent startGameEvent = new HappenedEvent(startDate, HappenedEventType.OTHER_EVENT, "{\"message\" : \"Игра началась\"}");
        happenedEventsRepository.save(startGameEvent);
        game.addHappenedEvent(startGameEvent);

        if (game.IsFinish()) {
            Date endDate = new Date();
            HappenedEvent endEvent = new HappenedEvent(endDate, HappenedEventType.OTHER_EVENT, "{\"message\" : \"Игра закончилась\"}");
            happenedEventsRepository.save(endEvent);
            game.addHappenedEvent(endEvent);
            game.Finish();
        }

        gameRepository.save(game);
    }

    @Override
    public List<HappenedEvent> getGameEvents(UUID gameId, boolean isManager) throws HttpException {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new HttpException("Game not found", HttpStatus.NOT_FOUND));

        if (game.getStatus() == GameStatus.DRAFT && !isManager) {
            throw new HttpException("Game is in draft status", HttpStatus.FORBIDDEN);
        }

        return game.getHappenedEvents();
    }
}
