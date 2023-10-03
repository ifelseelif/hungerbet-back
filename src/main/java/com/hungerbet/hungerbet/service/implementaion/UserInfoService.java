package com.hungerbet.hungerbet.service.implementaion;

import com.hungerbet.hungerbet.controllers.models.users.CreateUserRequest;
import com.hungerbet.hungerbet.entity.auth.UserInfoDetails;
import com.hungerbet.hungerbet.entity.domain.User;
import com.hungerbet.hungerbet.entity.exceptions.BadRequestException;
import com.hungerbet.hungerbet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    public UUID addUser(CreateUserRequest request) throws BadRequestException {
        Optional<User> userDetail = repository.findByLogin(request.getLogin());
        if(userDetail.isPresent()){
            throw new BadRequestException("User already created");
        }

        request.setPassword(encoder.encode(request.getPassword()));
        User user = new User(request.getFirstName(), request.getSecondName(), request.getLogin(), request.getPassword(), 1000);
        repository.save(user);

        return user.getId();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userDetail = repository.findByLogin(username);

        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }
}
