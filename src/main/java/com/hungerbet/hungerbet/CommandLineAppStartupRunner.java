package com.hungerbet.hungerbet;

import com.hungerbet.hungerbet.entity.domain.Game;
import com.hungerbet.hungerbet.entity.domain.GameStatus;
import com.hungerbet.hungerbet.entity.domain.Player;
import com.hungerbet.hungerbet.entity.domain.User;
import com.hungerbet.hungerbet.repository.GameRepository;
import com.hungerbet.hungerbet.repository.PlayerRepository;
import com.hungerbet.hungerbet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
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
    private PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        User admin = userRepository.findByLogin("admin").orElseGet(() -> {
            User adminUser = new User("Плутарх", "Хэвенсби", "admin", encoder.encode("admin"), "admin@email.ru");
            adminUser.setManager(true);
            userRepository.save(adminUser);
            return adminUser;
        });

        int randomNum = ThreadLocalRandom.current().nextInt(1, 100 + 1);
        Player player = new Player("Artur Clone " + randomNum, "Kuprtianov", 23, "male");
        participantRepository.save(player);

        if (gameRepository.findByName("Типа draft Голодные игры #75").isEmpty()) {
            Game game = new Game("Голодные игры DRAFT",
                    GameStatus.DRAFT,
                    new Date(2145, Calendar.JULY, 23),
                    "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.",
                    "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.",
                    "Джунгли",
                    admin);
            gameRepository.save(game);
        }

        if (gameRepository.findByName("Голодные игры #75").isEmpty()) {
            Game game = new Game("Голодные игры #75",
                    GameStatus.PLANNED,
                    new Date(2145, Calendar.JULY, 23),
                    "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.",
                    "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.",
                    "Джунгли",
                    admin);
            gameRepository.save(game);
        }

        if (gameRepository.findByName("Голодные игры #74").isEmpty()) {
            Game game = new Game("Голодные игры #74",
                    GameStatus.ONGOING,
                    new Date(2145, Calendar.JULY, 23),
                    "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.",
                    "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.",
                    "Лес",
                    admin);
            game.attachPlayer(player);
            gameRepository.save(game);
        }

        if (gameRepository.findByName("Голодные игры #73").isEmpty()) {
            Game game = new Game("Голодные игры #73",
                    GameStatus.COMPLETED,
                    new Date(2145, Calendar.JULY, 23),
                    "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.",
                    "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.",
                    "Разрушенный город",
                    admin);
            game.setWinner(player);
            game.attachPlayer(player);
            gameRepository.save(game);
        }

        if (gameRepository.findByName("Голодные игры #72").isEmpty()) {
            Game game = new Game("Голодные игры #72",
                    GameStatus.COMPLETED,
                    new Date(2145, Calendar.JULY, 23),
                    "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.",
                    "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.",
                    "Остров",
                    admin);
            game.setWinner(player);
            game.attachPlayer(player);
            gameRepository.save(game);
        }
    }
}
