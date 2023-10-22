package com.hungerbet.hungerbet;

import com.hungerbet.hungerbet.entity.domain.*;
import com.hungerbet.hungerbet.entity.exceptions.HttpException;
import com.hungerbet.hungerbet.init.Names;
import com.hungerbet.hungerbet.repository.*;
import org.antlr.v4.runtime.misc.Pair;
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
            adminUser.setBalanceMoney(200d);
            adminUser.setRole(Role.admin);
            userRepository.save(adminUser);
            return adminUser;
        });

        User service = userRepository.findByLogin("service").orElseGet(() -> {
            User adminUser = new User("service", "service", "service", encoder.encode("service"), "service@email.ru");
            adminUser.setRole(Role.admin);
            userRepository.save(adminUser);
            return adminUser;
        });

        //DRAFT - полностью не заполнена
        if (gameRepository.findByName("Голодные игры #1").isEmpty()) {
            Game game = new Game("Голодные игры #1", GameStatus.draft, new Date(2145, Calendar.JULY, 23), "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.", "", "", admin);

            gameRepository.save(game);
        }

        //DRAFT - частично заполнена
        if (gameRepository.findByName("Голодные игры #2").isEmpty()) {
            Game game = new Game("Голодные игры #2", GameStatus.draft, new Date(2145, Calendar.JULY, 23), "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.", "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.", "", admin);

            AddPlayersInGame(game, 24);
            gameRepository.save(game);
        }

        //DRAFT - готов к переводу в publish
        if (gameRepository.findByName("Голодные игры #3").isEmpty()) {
            Game game = new Game("Голодные игры #3", GameStatus.draft, new Date(2145, Calendar.JULY, 23), "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.", "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.", "Джунгли", admin);

            AddPlayersInGame(game, 24);
            gameRepository.save(game);
        }

        //Planned без игроков
        if (gameRepository.findByName("Голодные игры #4").isEmpty()) {
            Game game = new Game("Голодные игры #4", GameStatus.planned, new Date(2145, Calendar.JULY, 23), "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.", "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.", "Джунгли", admin);

            gameRepository.save(game);
        }

        //Planned с игроками но не полными
        if (gameRepository.findByName("Голодные игры #5").isEmpty()) {
            Game game = new Game("Голодные игры #5", GameStatus.planned, new Date(2145, Calendar.JULY, 23), "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.", "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.", "Разрушенный город", admin);

            AddPlayersInGame(game, 12);

            gameRepository.save(game);
        }

        //Planned с игроками
        if (gameRepository.findByName("Голодные игры #6").isEmpty()) {
            Game game = new Game("Голодные игры #6", GameStatus.planned, new Date(2145, Calendar.JULY, 23), "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.", "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.", "Разрушенный город", admin);

            AddPlayersInGame(game, 24);

            Calendar date = Calendar.getInstance();
            long timeInSecs = date.getTimeInMillis();
            Date afterAdding2Mins = new Date(timeInSecs + (2 * 60 * 1000));
            Date afterAdding5Mins = new Date(timeInSecs + (5 * 60 * 1000));

            PlannedEvent plannedEvent = new PlannedEvent(game.getId(), "Водопад", "Водопад", afterAdding2Mins);
            plannedEventRepository.save(plannedEvent);
            game.addPlannedEvent(plannedEvent);

            PlannedEvent plannedEvent1 = new PlannedEvent(game.getId(), "Армагедон", "Метеоритный дождь", afterAdding5Mins);
            plannedEventRepository.save(plannedEvent1);
            game.addPlannedEvent(plannedEvent1);

            gameRepository.save(game);
        }


        //Ongoing с игроками game 1
        if (gameRepository.findByName("Голодные игры #7").isEmpty()) {
            Game game = new Game("Голодные игры #7", GameStatus.ongoing, new Date(2145, Calendar.JULY, 23), "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.", "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.", "Разрушенный город", admin);

            AddPlayersInGame(game, 24);

            Calendar date = Calendar.getInstance();
            long timeInSecs = date.getTimeInMillis();

            Date before6Mins = new Date(timeInSecs - (200 * 60 * 1000));
            HappenedEvent happenedEvent = new HappenedEvent(before6Mins, HappenedEventType.other, EventBody.CreateOtherEvent(null, "Игра началась"));
            happenedEventsRepository.save(happenedEvent);
            game.addHappenedEvent(happenedEvent);

            HurtAndKillPlayers(game, 5, 15, 0, 119);

            Date before4Mins = new Date(timeInSecs - (50 * 60 * 1000));
            PlannedEvent plannedEvent = new PlannedEvent(game.getId(), "Водопад", "Водопад", before4Mins);
            plannedEvent.setHappened(true);
            plannedEventRepository.save(plannedEvent);

            Date afterAdding5Mins = new Date(timeInSecs + (5 * 60 * 1000));
            PlannedEvent anotherPlannedEvent = new PlannedEvent(game.getId(), "Армагедон", "Метеоритный дождь", afterAdding5Mins);
            plannedEventRepository.save(anotherPlannedEvent);

            game.setPlannedEvents(List.of(plannedEvent, anotherPlannedEvent));
            addHappenedEventForPlanned(game, plannedEvent);

            generateHappenedEvents(game, 20, 5, 3, 119);

            gameRepository.save(game);
        }

        //Ongoing с игроками game 2
        if (gameRepository.findByName("Голодные игры #8").isEmpty()) {
            Game game = new Game("Голодные игры #8", GameStatus.ongoing, new Date(2145, Calendar.JULY, 23), "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.", "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.", "Разрушенный город", admin);

            AddPlayersInGame(game, 24);

            Calendar date = Calendar.getInstance();
            long timeInSecs = date.getTimeInMillis();

            Date beforeDelta = new Date(timeInSecs - (15 * 60 * 1000));
            HappenedEvent happenedEvent = new HappenedEvent(beforeDelta, HappenedEventType.other, EventBody.CreateOtherEvent(null, "Игра началась"));
            happenedEventsRepository.save(happenedEvent);
            game.addHappenedEvent(happenedEvent);

            HurtAndKillPlayers(game, 2, 10, 0, 14);

            Date before4Mins = new Date(timeInSecs - (10 * 60 * 1000));
            PlannedEvent plannedEvent = new PlannedEvent(game.getId(), "Армагедон", "Метеоритный дождь", before4Mins);
            plannedEvent.setHappened(true);
            plannedEventRepository.save(plannedEvent);

            Date afterAdding5Mins = new Date(timeInSecs + (2 * 60 * 1000));
            PlannedEvent anotherPlannedEvent = new PlannedEvent(game.getId(), "Доджь из токсинов", "Токсичный доджь", afterAdding5Mins);
            plannedEventRepository.save(anotherPlannedEvent);

            game.setPlannedEvents(List.of(plannedEvent, anotherPlannedEvent));
            addHappenedEventForPlanned(game, plannedEvent);

            generateHappenedEvents(game, 40, 3, 0, 14);

            gameRepository.save(game);
        }

        //Finish с игроками game 2
        if (gameRepository.findByName("Голодные игры #9").isEmpty()) {
            Game game = new Game("Голодные игры #9", GameStatus.completed, new Date(2145, Calendar.JULY, 23), "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.", "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.", "Разрушенный город", admin);

            AddPlayersInGame(game, 24);

            Calendar date = Calendar.getInstance();
            long timeInSecs = date.getTimeInMillis();

            Date beforeDelta = new Date(timeInSecs - (300 * 60 * 1000));
            HappenedEvent happenedEvent = new HappenedEvent(beforeDelta, HappenedEventType.other, EventBody.CreateOtherEvent(null, "Игра началась"));
            happenedEventsRepository.save(happenedEvent);
            game.addHappenedEvent(happenedEvent);


            Player winner = game.getPlayers().stream().filter(player -> !player.isDead()).findFirst().orElseThrow(() -> new RuntimeException("ERROR WINNER NOT ONLY ONE"));
            game.setWinner(winner);

            generateHappenedEvents(game, 30, 19, 6, 299);

            Date before6Mins = new Date(timeInSecs - (10 * 60 * 1000));
            PlannedEvent plannedEvent = new PlannedEvent(game.getId(), "Доджь из токсинов", "Токсичный доджь", before6Mins);
            plannedEvent.setHappened(true);
            plannedEventRepository.save(plannedEvent);


            game.addPlannedEvent(plannedEvent);
            addHappenedEventForPlanned(game, plannedEvent);

            Date endDate = new Date(timeInSecs - (5 * 60 * 1000));
            HappenedEvent happenedEventEndGame = new HappenedEvent(endDate, HappenedEventType.other, EventBody.CreateOtherEvent(null, "Игра закончилась"));
            happenedEventsRepository.save(happenedEventEndGame);

            HurtAndKillPlayers(game, 23, 19, 6, 229);

            gameRepository.save(game);
        }

        //Finish с игроками game 2
        if (gameRepository.findByName("Голодные игры #10").isEmpty()) {
            Game game = new Game("Голодные игры #10", GameStatus.completed, new Date(2145, Calendar.JULY, 23), "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.", "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.", "Разрушенный город", admin);

            AddPlayersInGame(game, 24);
            gameRepository.save(game);

            Calendar date = Calendar.getInstance();
            long timeInSecs = date.getTimeInMillis();

            Date beforeDelta = new Date(timeInSecs - (120 * 60 * 1000));
            HappenedEvent happenedEvent = new HappenedEvent(beforeDelta, HappenedEventType.other, EventBody.CreateOtherEvent(null, "Игра началась"));
            happenedEventsRepository.save(happenedEvent);
            game.addHappenedEvent(happenedEvent);

            generateHappenedEvents(game, 48, 20, 11, 119);

            Player winner = game.getPlayers().stream().filter(player -> !player.isDead()).findFirst().orElseThrow(() -> new RuntimeException("ERROR WINNER NOT ONLY ONE"));
            game.setWinner(winner);

            Date beforeMins = new Date(timeInSecs - (15 * 60 * 1000));
            PlannedEvent plannedEvent = new PlannedEvent(game.getId(), "Огеннные камни с неба", "Армагедон", beforeMins);
            plannedEvent.setHappened(true);
            plannedEventRepository.save(plannedEvent);

            game.addPlannedEvent(plannedEvent);
            addHappenedEventForPlanned(game, plannedEvent);

            Date endDate = new Date(timeInSecs - (10 * 60 * 1000));
            HappenedEvent happenedEventEndGame = new HappenedEvent(endDate, HappenedEventType.other, EventBody.CreateOtherEvent(null, "Игра закончилась"));
            happenedEventsRepository.save(happenedEventEndGame);
            game.addHappenedEvent(happenedEventEndGame);

            HurtAndKillPlayers(game, 23, 15, 11, 119);

            gameRepository.save(game);
        }
    }

    private void AddPlayersInGame(Game game, int count) throws HttpException {

        List<Player> playerList = new ArrayList<>();
        for (int i = 0; i < count / 2; i++) {
            Pair<String, String> pair = Names.mansNames.get(i);
            Player player = new Player(pair.a, pair.b, 23, "male");
            playerRepository.save(player);
            playerList.add(player);
        }

        for (int i = 0; i < count / 2; i++) {
            Pair<String, String> pair = Names.womanNames.get(i);
            Player player = new Player(pair.a, pair.b, 23, "female");
            playerRepository.save(player);
            playerList.add(player);
        }

        for (int i = 0; i < count; i++) {
            game.attachPlayer(playerList.get(i));
        }
    }

    private void HurtAndKillPlayers(Game game, int killedPlayers, int hurtPlayers, int start, int end) {
        game.getPlayers().stream().filter(player -> player.getState() != PlayerState.dead).limit(hurtPlayers).forEach(player -> {
            int i = getI();
            if (i % 3 == 0) {
                HappenedEvent happenedEvent = new HappenedEvent(GetRandomDate(start, end), HappenedEventType.player_injured, EventBody.CreatePlayerEvent(player.getId(), PlayerState.moderate_injury));
                game.addHappenedEvent(happenedEvent);
                happenedEventsRepository.save(happenedEvent);

                player.setState(PlayerState.moderate_injury);
                playerRepository.save(player);

            } else if (i % 2 == 0) {
                HappenedEvent happenedEvent = new HappenedEvent(GetRandomDate(start, end), HappenedEventType.player_injured, EventBody.CreatePlayerEvent(player.getId(), PlayerState.severe_injury));
                game.addHappenedEvent(happenedEvent);
                happenedEventsRepository.save(happenedEvent);

                player.setState(PlayerState.severe_injury);
                playerRepository.save(player);
            } else {
                HappenedEvent happenedEvent = new HappenedEvent(GetRandomDate(start, end), HappenedEventType.player_injured, EventBody.CreatePlayerEvent(player.getId(), PlayerState.slight_injury));
                game.addHappenedEvent(happenedEvent);
                happenedEventsRepository.save(happenedEvent);

                player.setState(PlayerState.slight_injury);
                playerRepository.save(player);
            }
        });

        game.getPlayers().stream().filter(player -> player.getState() != PlayerState.dead).limit(killedPlayers).forEach(player -> {
            HappenedEvent happenedEvent = new HappenedEvent(GetRandomDate(start, end), HappenedEventType.player_killed, EventBody.CreatePlayerEvent(player.getId(), PlayerState.dead));
            game.addHappenedEvent(happenedEvent);
            happenedEventsRepository.save(happenedEvent);

            player.setState(PlayerState.dead);
            playerRepository.save(player);
        });
    }

    private Date GetRandomDate(int start, int end) {
        Random random = new Random();

        Calendar date = Calendar.getInstance();
        long timeInSecs = date.getTimeInMillis();

        return new Date(timeInSecs - ((random.nextInt(end + 1 - start) + start) * 60 * 1000));
    }


    private void addHappenedEventForPlanned(Game game, PlannedEvent plannedEvent) {
        HappenedEvent happenedEventForPlanned = new HappenedEvent(plannedEvent.getDateStart(), HappenedEventType.random, EventBody.CreatePlannedEvent(plannedEvent.getId()));
        happenedEventsRepository.save(happenedEventForPlanned);
        game.addHappenedEvent(happenedEventForPlanned);
    }

    private void generateHappenedEvents(Game game, int countSupply, int countOther, int start, int end) {

        for (int i = 0; i < countOther; i++) {
            int playerRnd = new Random().nextInt(game.getPlayers().size());
            Player pLayer = game.getPlayers().get(playerRnd);
            Date happenedTime = GetRandomDate(start, end);
            HappenedEvent event = new HappenedEvent(happenedTime, HappenedEventType.other, EventBody.CreateOtherEvent(pLayer.getId(), "достиг рога изобилия"));
            happenedEventsRepository.save(event);
            game.addHappenedEvent(event);
        }
        for (int i = 0; i < countSupply; i++) {
            int playerRnd = new Random().nextInt(game.getPlayers().size());
            Player pLayer = game.getPlayers().get(playerRnd);
            Date happenedTime = GetRandomDate(start, end);
            int itemRnd = new Random().nextInt(Names.items.size());
            String itemName = Names.items.get(itemRnd);
            HappenedEvent event = new HappenedEvent(happenedTime, HappenedEventType.supply, EventBody.CreateSupplyEvent(pLayer.getId(), itemName));
            happenedEventsRepository.save(event);
            game.addHappenedEvent(event);
        }
    }


    private int i;

    private int getI() {
        return i++;
    }
}
