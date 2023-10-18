package com.hungerbet.hungerbet;

import com.hungerbet.hungerbet.entity.domain.*;
import com.hungerbet.hungerbet.repository.GameRepository;
import com.hungerbet.hungerbet.repository.PlannedEventRepository;
import com.hungerbet.hungerbet.repository.PlayerRepository;
import com.hungerbet.hungerbet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository participantRepository;

    @Autowired
    private PlannedEventRepository plannedEventRepository;

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

        if (gameRepository.findByName("Типа draft Голодные игры #75").isEmpty()) {
            Game game = new Game("Типа draft Голодные игры #75",
                    GameStatus.DRAFT,
                    new Date(2145, Calendar.JULY, 23),
                    "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.",
                    "",
                    "",
                    admin);

            gameRepository.save(game);
        }

        if (gameRepository.findByName("Голодные игры #75 ready for PLANNED").isEmpty()) {
            Game game = new Game("Голодные игры #75 ready for PLANNED",
                    GameStatus.DRAFT,
                    new Date(2145, Calendar.JULY, 23),
                    "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.",
                    "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.",
                    "Джунгли",
                    admin);

            gameRepository.save(game);
        }

        if (gameRepository.findByName("Голодные игры #75 ready for ONGOING").isEmpty()) {
            Game game = new Game("Голодные игры #75 ready for ONGOING",
                    GameStatus.PLANNED,
                    new Date(2145, Calendar.JULY, 23),
                    "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.",
                    "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.",
                    "Джунгли",
                    admin);

            List<Player> playerList = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                int randomNum = ThreadLocalRandom.current().nextInt(1, 100 + 1);
                Player player = new Player("Artur Clone " + randomNum, "Kuprtianov", 23, "male");
                participantRepository.save(player);
                playerList.add(player);
            }

            for (int i = 0; i < 12; i++) {
                int randomNum = ThreadLocalRandom.current().nextInt(1, 100 + 1);
                Player player = new Player("Maria Clone " + randomNum, "Kuprtianova", 23, "female");
                playerList.add(player);
                participantRepository.save(player);
            }


            for (int i = 0; i < 24; i++) {
                game.attachPlayer(playerList.get(i));
            }

            gameRepository.save(game);
        }


        if (gameRepository.findByName("Голодные игры #73 ONGOING").isEmpty()) {
            Game game = new Game("Голодные игры #73 ONGOING",
                    GameStatus.ONGOING,
                    new Date(2145, Calendar.JULY, 23),
                    "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.",
                    "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.",
                    "Разрушенный город",
                    admin);

            List<Player> playerList = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                int randomNum = ThreadLocalRandom.current().nextInt(1, 100 + 1);
                Player player = new Player("Artur Clone " + randomNum, "Kuprtianov", 23, "male");
                participantRepository.save(player);
                playerList.add(player);
            }

            for (int i = 0; i < 12; i++) {
                int randomNum = ThreadLocalRandom.current().nextInt(1, 100 + 1);
                Player player = new Player("Maria Clone " + randomNum, "Kuprtianova", 23, "female");
                participantRepository.save(player);
                playerList.add(player);
            }

            for (int i = 0; i < 24; i++) {
                game.attachPlayer(playerList.get(i));
            }

            gameRepository.save(game);
            Calendar date = Calendar.getInstance();
            long timeInSecs = date.getTimeInMillis();
            Date afterAdding2Mins = new Date(timeInSecs + (2 * 60 * 1000));
            Date afterAdding5Mins = new Date(timeInSecs + (5 * 60 * 1000));
            plannedEventRepository.save(new PlannedEvent(game.getId(), "Армагедон", "Большой бум", afterAdding2Mins));
            plannedEventRepository.save(new PlannedEvent(game.getId(), "Водопад", "Много воды", afterAdding5Mins));
        }

        if (gameRepository.findByName("Голодные игры #72").isEmpty()) {
            Game game = new Game("Голодные игры #72",
                    GameStatus.COMPLETED,
                    new Date(2145, Calendar.JULY, 23),
                    "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.",
                    "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.",
                    "Остров",
                    admin);

            int randomNum = ThreadLocalRandom.current().nextInt(1, 100 + 1);
            Player player = new Player("Maria Clone " + randomNum, "Kuprtianova", 23, "female");
            participantRepository.save(player);

            game.attachPlayer(player);
            game.setWinner(player);
            gameRepository.save(game);
        }
    }
}
