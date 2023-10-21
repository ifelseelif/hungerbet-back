package com.hungerbet.hungerbet;

import com.hungerbet.hungerbet.entity.domain.*;
import com.hungerbet.hungerbet.entity.exceptions.HttpException;
import com.hungerbet.hungerbet.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlannedEventRepository plannedEventRepository;

    @Autowired
    private HappenedEventsRepository happenedEventsRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        User admin = userRepository.findByLogin("admin").orElseGet(() -> {
            User adminUser = new User("Плутарх", "Хэвенсби", "admin", encoder.encode("admin"), "admin@email.ru");
            adminUser.setManager(true);
            userRepository.save(adminUser);
            return adminUser;
        });

        User service = userRepository.findByLogin("service").orElseGet(() -> {
            User adminUser = new User("service", "service", "service", encoder.encode("service"), "service@email.ru");
            adminUser.setManager(true);
            userRepository.save(adminUser);
            return adminUser;
        });

        //DRAFT - полностью не заполнена
        if (gameRepository.findByName("Голодные игры #1").isEmpty()) {
            Game game = new Game("Голодные игры #1",
                    GameStatus.draft,
                    new Date(2145, Calendar.JULY, 23),
                    "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.",
                    "",
                    "",
                    admin);

            gameRepository.save(game);
        }

        //DRAFT - частично заполнена
        if (gameRepository.findByName("Голодные игры #2").isEmpty()) {
            Game game = new Game("Голодные игры #2",
                    GameStatus.draft,
                    new Date(2145, Calendar.JULY, 23),
                    "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.",
                    "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.",
                    "",
                    admin);

            AddPlayersInGame(game, 24);
            gameRepository.save(game);
        }

        //DRAFT - готов к переводу в publish
        if (gameRepository.findByName("Голодные игры #4").isEmpty()) {
            Game game = new Game("Голодные игры #4",
                    GameStatus.draft,
                    new Date(2145, Calendar.JULY, 23),
                    "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.",
                    "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.",
                    "Джунгли",
                    admin);

            AddPlayersInGame(game, 24);
            gameRepository.save(game);
        }

        //Planned без игроков
        if (gameRepository.findByName("Голодные игры #5").isEmpty()) {
            Game game = new Game("Голодные игры #5",
                    GameStatus.planned,
                    new Date(2145, Calendar.JULY, 23),
                    "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.",
                    "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.",
                    "Джунгли",
                    admin);

            gameRepository.save(game);
        }

        //Planned с игроками
        if (gameRepository.findByName("Голодные игры #6").isEmpty()) {
            Game game = new Game("Голодные игры #6",
                    GameStatus.planned,
                    new Date(2145, Calendar.JULY, 23),
                    "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.",
                    "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.",
                    "Разрушенный город",
                    admin);

            AddPlayersInGame(game, 24);

            gameRepository.save(game);
            Calendar date = Calendar.getInstance();
            long timeInSecs = date.getTimeInMillis();
            Date afterAdding2Mins = new Date(timeInSecs + (2 * 60 * 1000));
            Date afterAdding5Mins = new Date(timeInSecs + (5 * 60 * 1000));
            plannedEventRepository.save(new PlannedEvent(game.getId(), "Водопад", "Водопад", afterAdding2Mins));
            plannedEventRepository.save(new PlannedEvent(game.getId(), "Армагедон", "Метеоритный дождь", afterAdding5Mins));
        }


        //Ongoing с игроками game 1
        if (gameRepository.findByName("Голодные игры #6").isEmpty()) {
            Game game = new Game("Голодные игры #6",
                    GameStatus.ongoing,
                    new Date(2145, Calendar.JULY, 23),
                    "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.",
                    "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.",
                    "Разрушенный город",
                    admin);

            AddPlayersInGame(game, 24);

            gameRepository.save(game);

            Calendar date = Calendar.getInstance();
            long timeInSecs = date.getTimeInMillis();

            Date before6Mins = new Date(timeInSecs - (6 * 60 * 1000));
            HappenedEvent happenedEvent = new HappenedEvent(before6Mins, HappenedEventType.other, EventBody.CreateOtherEvent(null, "Игра началась"));
            happenedEventsRepository.save(happenedEvent);
            game.addHappenedEvent(happenedEvent);

            HurtAndKillPlayers(game, 5, 15, 3);

            Date before4Mins = new Date(timeInSecs - (4 * 60 * 1000));
            PlannedEvent plannedEvent = new PlannedEvent(game.getId(), "Водопад", "Водопад", before4Mins);

            AddPlannedThatHappened(game, plannedEvent);

            Date afterAdding5Mins = new Date(timeInSecs + (5 * 60 * 1000));
            plannedEventRepository.save(new PlannedEvent(game.getId(), "Армагедон", "Метеоритный дождь", afterAdding5Mins));
        }

        //Ongoing с игроками game 2
        if (gameRepository.findByName("Голодные игры #7").isEmpty()) {
            Game game = new Game("Голодные игры #7",
                    GameStatus.ongoing,
                    new Date(2145, Calendar.JULY, 23),
                    "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.",
                    "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.",
                    "Разрушенный город",
                    admin);

            AddPlayersInGame(game, 24);
            gameRepository.save(game);

            Calendar date = Calendar.getInstance();
            long timeInSecs = date.getTimeInMillis();

            Date beforeDelta = new Date(timeInSecs - (15 * 60 * 1000));
            HappenedEvent happenedEvent = new HappenedEvent(beforeDelta, HappenedEventType.other, EventBody.CreateOtherEvent(null, "Игра началась"));
            happenedEventsRepository.save(happenedEvent);
            game.addHappenedEvent(happenedEvent);

            HurtAndKillPlayers(game, 20, 2, 10);

            Date before4Mins = new Date(timeInSecs - (10 * 60 * 1000));
            PlannedEvent plannedEvent = new PlannedEvent(game.getId(), "Армагедон", "Метеоритный дождь", before4Mins);

            AddPlannedThatHappened(game, plannedEvent);

            Date afterAdding5Mins = new Date(timeInSecs + (2 * 60 * 1000));
            plannedEventRepository.save(new PlannedEvent(game.getId(), "Доджь из токсинов", "Токсичный доджь", afterAdding5Mins));
        }

        //Finish с игроками game 2
        if (gameRepository.findByName("Голодные игры #8").isEmpty()) {
            Game game = new Game("Голодные игры #8",
                    GameStatus.completed,
                    new Date(2145, Calendar.JULY, 23),
                    "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.",
                    "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.",
                    "Разрушенный город",
                    admin);

            AddPlayersInGame(game, 24);
            gameRepository.save(game);

            Calendar date = Calendar.getInstance();
            long timeInSecs = date.getTimeInMillis();

            Date beforeDelta = new Date(timeInSecs - (15 * 60 * 1000));
            HappenedEvent happenedEvent = new HappenedEvent(beforeDelta, HappenedEventType.other, EventBody.CreateOtherEvent(null, "Игра началась"));
            happenedEventsRepository.save(happenedEvent);
            game.addHappenedEvent(happenedEvent);

            HurtAndKillPlayers(game, 23, 0, 10);

            Player winner = game.getPlayers().stream().filter(player -> !player.isDead()).findFirst().orElseThrow(() -> new RuntimeException("ERROR WINNER NOT ONLY ONE"));
            game.setWinner(winner);
            gameRepository.save(game);

            Date before6Mins = new Date(timeInSecs - (6 * 60 * 1000));
            PlannedEvent plannedEvent = new PlannedEvent(game.getId(), "Доджь из токсинов", "Токсичный доджь", before6Mins);

            AddPlannedThatHappened(game, plannedEvent);

            Date endDate = new Date(timeInSecs - (5 * 60 * 1000));
            HappenedEvent happenedEventEndGame = new HappenedEvent(endDate, HappenedEventType.other, EventBody.CreateOtherEvent(null, "Игра закончилась"));
            happenedEventsRepository.save(happenedEventEndGame);
            game.addHappenedEvent(happenedEventEndGame);
        }

        //Finish с игроками game 2
        if (gameRepository.findByName("Голодные игры #9").isEmpty()) {
            Game game = new Game("Голодные игры #9",
                    GameStatus.completed,
                    new Date(2145, Calendar.JULY, 23),
                    "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.",
                    "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.",
                    "Разрушенный город",
                    admin);

            AddPlayersInGame(game, 24);
            gameRepository.save(game);

            Calendar date = Calendar.getInstance();
            long timeInSecs = date.getTimeInMillis();

            Date beforeDelta = new Date(timeInSecs - (30 * 60 * 1000));
            HappenedEvent happenedEvent = new HappenedEvent(beforeDelta, HappenedEventType.other, EventBody.CreateOtherEvent(null, "Игра началась"));
            happenedEventsRepository.save(happenedEvent);
            game.addHappenedEvent(happenedEvent);

            HurtAndKillPlayers(game, 23, 0, 10);

            Player winner = game.getPlayers().stream().filter(player -> !player.isDead()).findFirst().orElseThrow(() -> new RuntimeException("ERROR WINNER NOT ONLY ONE"));
            game.setWinner(winner);
            gameRepository.save(game);

            Date before6Mins = new Date(timeInSecs - (15 * 60 * 1000));
            PlannedEvent plannedEvent = new PlannedEvent(game.getId(), "Огеннные камни с неба", "Армагедон", before6Mins);

            AddPlannedThatHappened(game, plannedEvent);

            Date endDate = new Date(timeInSecs - (10 * 60 * 1000));
            HappenedEvent happenedEventEndGame = new HappenedEvent(endDate, HappenedEventType.other, EventBody.CreateOtherEvent(null, "Игра закончилась"));
            happenedEventsRepository.save(happenedEventEndGame);
            game.addHappenedEvent(happenedEventEndGame);
        }
    }

    private void AddPlayersInGame(Game game, int count) throws HttpException {
        List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < count / 2; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(1, 100 + 1);
            Player player = new Player("Artur Clone " + randomNum, "Kuprtianov", 23, "male");
            playerRepository.save(player);
            playerList.add(player);
        }

        for (int i = 0; i < count / 2; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(1, 100 + 1);
            Player player = new Player("Maria Clone " + randomNum, "Kuprtianova", 23, "female");
            playerRepository.save(player);
            playerList.add(player);
        }

        for (int i = 0; i < 24; i++) {
            game.attachPlayer(playerList.get(i));
        }
    }

    private void HurtAndKillPlayers(Game game, int killedPlayers, int hurtPlayers, int delta) throws HttpException {
        Calendar date = Calendar.getInstance();
        long timeInSecs = date.getTimeInMillis();

        Date beforeDeltaMins = new Date(timeInSecs - (delta * 60 * 1000));

        game.getPlayers().stream().filter(player -> player.getState() != PlayerState.dead).limit(killedPlayers).forEach(player -> {
            HappenedEvent happenedEvent = new HappenedEvent(beforeDeltaMins, HappenedEventType.player, EventBody.CreatePlayerEvent(player.getId(), PlayerState.dead));
            game.addHappenedEvent(happenedEvent);
            happenedEventsRepository.save(happenedEvent);

            player.setState(PlayerState.dead);
            playerRepository.save(player);
        });

        game.getPlayers().stream().filter(player -> player.getState() != PlayerState.dead).limit(hurtPlayers).forEach(player -> {
            int i = getI();
            if (i % 3 == 0) {
                HappenedEvent happenedEvent = new HappenedEvent(beforeDeltaMins, HappenedEventType.player, EventBody.CreatePlayerEvent(player.getId(), PlayerState.moderate_injury));
                game.addHappenedEvent(happenedEvent);
                happenedEventsRepository.save(happenedEvent);

                player.setState(PlayerState.moderate_injury);
                playerRepository.save(player);

            } else if (i % 2 == 0) {
                HappenedEvent happenedEvent = new HappenedEvent(beforeDeltaMins, HappenedEventType.player, EventBody.CreatePlayerEvent(player.getId(), PlayerState.severe_injury));
                game.addHappenedEvent(happenedEvent);
                happenedEventsRepository.save(happenedEvent);

                player.setState(PlayerState.severe_injury);
                playerRepository.save(player);
            } else if (i % 5 == 0) {
                HappenedEvent happenedEvent = new HappenedEvent(beforeDeltaMins, HappenedEventType.player, EventBody.CreatePlayerEvent(player.getId(), PlayerState.slight_injury));
                game.addHappenedEvent(happenedEvent);
                happenedEventsRepository.save(happenedEvent);

                player.setState(PlayerState.slight_injury);
                playerRepository.save(player);
            }
        });
    }

    private void AddPlannedThatHappened(Game game, PlannedEvent plannedEvent) {
        plannedEvent.setHappened(true);
        plannedEventRepository.save(plannedEvent);
        HappenedEvent happenedEventForPlanned = new HappenedEvent(plannedEvent.getDateStart(), HappenedEventType.random, EventBody.CreatePlannedEvent(plannedEvent.getId()));
        happenedEventsRepository.save(happenedEventForPlanned);
        game.addHappenedEvent(happenedEventForPlanned);
    }

    private int i;

    private int getI() {
        return i++;
    }
}
