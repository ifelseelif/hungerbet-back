package com.hungerbet.hungerbet;

import com.hungerbet.hungerbet.entity.domain.*;
import com.hungerbet.hungerbet.repository.GameRepository;
import com.hungerbet.hungerbet.repository.ManagerRepository;
import com.hungerbet.hungerbet.repository.ParticipantRepository;
import com.hungerbet.hungerbet.repository.UserRepository;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(String...args) throws Exception {
        Optional<User> user = userRepository.findByLogin("admin");
        Manager manager = null;
        if(user.isEmpty()){
            User adminUser = new User("Плутарх","Хэвенсби","admin",encoder.encode("admin"),0);
            userRepository.save(adminUser);
            manager = new Manager();
            manager.setUser(adminUser);
            managerRepository.save(manager);
        }

        int randomNum = ThreadLocalRandom.current().nextInt(1, 100 + 1);
        Participant participant = new Participant("Artur", "Kuprtianov", randomNum, 1);
        participantRepository.save(participant);

        if(gameRepository.findByName("Голодные игры #75").isEmpty()) {
            Game game = new Game("Голодные игры #75",
                    GameStatus.PLANNED,
                    new Date(2145, Calendar.JULY, 23),
                    "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.",
                    "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.");
            game.setManager(manager);
            gameRepository.save(game);
        }

        if(gameRepository.findByName("Голодные игры #74").isEmpty()) {
            Game game = new Game("Голодные игры #74",
                    GameStatus.ONGOING,
                    new Date(2145, Calendar.JULY, 23),
                    "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.",
                    "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.");
            game.setManager(manager);
            game.addParticipant(participant);
            gameRepository.save(game);
        }

        if(gameRepository.findByName("Голодные игры #73").isEmpty()) {
            Game game = new Game("Голодные игры #73",
                    GameStatus.COMPLETED,
                    new Date(2145, Calendar.JULY, 23),
                    "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.",
                    "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.");
            game.setManager(manager);
            game.setWinner(participant);
            gameRepository.save(game);
        }

        if(gameRepository.findByName("Голодные игры #72").isEmpty()) {
            Game game = new Game("Голодные игры #72",
                    GameStatus.COMPLETED,
                    new Date(2145, Calendar.JULY, 23),
                    "Состоит из 12 секторов, в каждом из которых по очереди активируется определенное опасное явление. Рог Изобилия находится посередине и представляет собой остров, окруженный соленой водой.",
                    "Квартальная бойня. Все участники являются победителями прошлых игр. Единственный источник воды - стволы деревьев, растущие в лесу.");
            game.setManager(manager);
            game.setWinner(participant);
            gameRepository.save(game);
        }
    }
}
