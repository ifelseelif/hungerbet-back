package com.hungerbet.hungerbet.service.implementaion;

import com.hungerbet.hungerbet.entity.domain.Game;
import com.hungerbet.hungerbet.entity.domain.GameStatus;
import com.hungerbet.hungerbet.entity.domain.HappenedEvent;
import com.hungerbet.hungerbet.entity.domain.HappenedEventType;
import com.hungerbet.hungerbet.entity.exceptions.BadRequestException;
import com.hungerbet.hungerbet.entity.exceptions.NotFoundException;
import com.hungerbet.hungerbet.repository.GameRepository;
import com.hungerbet.hungerbet.repository.HappenedEventsRepository;
import com.hungerbet.hungerbet.service.GameService;
import com.hungerbet.hungerbet.service.models.game.CreateGameModel;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public Game create(CreateGameModel createGameModel) {
        Game game = new Game(createGameModel.getName(), GameStatus.DRAFT, createGameModel.getStartedAt(),
                createGameModel.getArenInfo(), createGameModel.getDescription());
        gameRepository.save(game);

        return game;
    }

    @Override
    public Game publishGame(UUID gameId) throws NotFoundException, BadRequestException {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new NotFoundException("Game not found"));

        game.publish();
        gameRepository.save(game);

        return game;
    }

    @Override
    public Game getGame(UUID gameId) throws NotFoundException {
        return gameRepository.findById(gameId).orElseThrow(() -> new NotFoundException("Game not found"));
    }

    @Override
    public List<Game> getGames() {
        return gameRepository.findAll();
    }

    @Override
    public List<Game> getPublishedGames() {
        List<GameStatus> gameStatuses = List.of(GameStatus.PLANNED, GameStatus.ONGOING);
        return gameRepository.findPublishedGames(gameStatuses);
    }

    @Override
    public Game startGame(UUID gameId) throws NotFoundException, BadRequestException {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new NotFoundException("Game not found"));

        game.start();

        Date currentDate = new Date();
        HappenedEvent startGameEvent = new HappenedEvent("Игра началась", "", currentDate, HappenedEventType.OTHERS);
        happenedEventsRepository.save(startGameEvent);
        game.addHappenedEvent(startGameEvent);

        if(game.IsFinish()){
            HappenedEvent happenedEvent = new HappenedEvent("Игра закончилась", "", currentDate, HappenedEventType.OTHERS);
            happenedEventsRepository.save(happenedEvent);
            game.addHappenedEvent(happenedEvent);
            game.Finish();
        }

        gameRepository.save(game);

        return game;
    }
}
