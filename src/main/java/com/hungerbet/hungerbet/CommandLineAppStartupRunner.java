package com.hungerbet.hungerbet;

import com.hungerbet.hungerbet.entity.domain.Manager;
import com.hungerbet.hungerbet.entity.domain.User;
import com.hungerbet.hungerbet.repository.ManagerRepository;
import com.hungerbet.hungerbet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.apache.logging.log4j.ThreadContext.isEmpty;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(String...args) throws Exception {
        Optional<User> user = userRepository.findByLogin("admin");
        if(user.isEmpty()){
            User adminUser = new User("admin","admin","admin",encoder.encode("admin"),0);
            userRepository.save(adminUser);
            Manager manager = new Manager();
            manager.setUser(adminUser);
            managerRepository.save(manager);
        }else {
            managerRepository.delete(user.get().getManager());
            userRepository.delete(user.get());
            User adminUser = new User("admin","admin","admin",encoder.encode("admin"),0);
            userRepository.save(adminUser);
            Manager manager = new Manager();
            manager.setUser(adminUser);
            managerRepository.save(manager);
        }
    }
}
