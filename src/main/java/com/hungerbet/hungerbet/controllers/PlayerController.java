package com.hungerbet.hungerbet.controllers;

import com.hungerbet.hungerbet.controllers.models.player.CreatePlayerRequest;
import com.hungerbet.hungerbet.controllers.models.player.PlayerResponse;
import com.hungerbet.hungerbet.entity.domain.Player;
import com.hungerbet.hungerbet.entity.exceptions.HttpException;
import com.hungerbet.hungerbet.service.PlayerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/players")
@CrossOrigin(origins = "*")
@PreAuthorize("hasAuthority('Manager')")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping
    public PlayerResponse create(@RequestBody CreatePlayerRequest request) throws HttpException {
        Player player = playerService.addParticipant(request);
        return new PlayerResponse(player);
    }

    @GetMapping("/all")
    public List<PlayerResponse> getAll() {
        return playerService.getAllParticipants().stream().map(PlayerResponse::new).toList();
    }
}
