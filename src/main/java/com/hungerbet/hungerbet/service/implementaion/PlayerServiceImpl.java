package com.hungerbet.hungerbet.service.implementaion;

import com.hungerbet.hungerbet.controllers.models.player.CreatePlayerRequest;
import com.hungerbet.hungerbet.entity.domain.Game;
import com.hungerbet.hungerbet.entity.domain.Player;
import com.hungerbet.hungerbet.entity.exceptions.HttpException;
import com.hungerbet.hungerbet.repository.GameRepository;
import com.hungerbet.hungerbet.repository.PlayerRepository;
import com.hungerbet.hungerbet.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameRepository gameRepository;

    @Override
    public Player addParticipant(CreatePlayerRequest request) {
        Player newParticipant = new Player(request.getFirstName(), request.getLastName(), request.getAge(), request.getGender(), request.getDistrict());
        playerRepository.save(newParticipant);

        return newParticipant;
    }

    @Override
    public List<Player> getAllParticipants() {
        return playerRepository.findAll();
    }

    @Override
    public void attach(Game game, UUID playerId) throws HttpException {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new HttpException("Participant not found", HttpStatus.NOT_FOUND));
        game.attachPlayer(player);
    }
}
