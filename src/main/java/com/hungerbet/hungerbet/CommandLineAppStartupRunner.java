package com.hungerbet.hungerbet;

import com.hungerbet.hungerbet.entity.domain.Manager;
import com.hungerbet.hungerbet.entity.domain.User;
import com.hungerbet.hungerbet.repository.ManagerRepository;
import com.hungerbet.hungerbet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public void run(String...args) throws Exception {
        if(userRepository.findByLogin("admin").isEmpty()){
            User user = new User("admin","admin","admin","admin",0);
            userRepository.save(user);
            Manager manager = new Manager();
            manager.setUser(user);
            managerRepository.save(manager);
        }
    }
}
