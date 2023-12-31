package com.hungerbet.hungerbet.controllers.models.game;

import com.hungerbet.hungerbet.controllers.models.events.EventResponse;
import com.hungerbet.hungerbet.controllers.models.plannedEvents.PlannedEventResponse;
import com.hungerbet.hungerbet.controllers.models.player.PlayerResponse;
import com.hungerbet.hungerbet.controllers.models.users.UserResponseModel;
import com.hungerbet.hungerbet.entity.domain.Game;
import com.hungerbet.hungerbet.entity.domain.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameResponse {

    private UUID id;
    private String name;
    private String arenaType;
    private String arenaDescription;
    private Date dateStart;
    private Date dateEnd;
    private String status;
    private String description;
    private UserResponseModel manager;
    private PlayerResponse winner;
    private Map<Integer, List<PlayerResponse>> players;
    private List<PlannedEventResponse> plannedEvents;
    private List<EventResponse> happenedEvents;

    public GameResponse(Game game) {
        this.id = game.getId();
        this.name = game.getName();
        this.arenaType = game.getArenaType();
        this.arenaDescription = game.getArenaDescription();
        this.dateStart = game.getDateStart();
        this.dateEnd = game.getDateEnd();
        this.status = game.getStatus().toString();
        this.description = game.getDescription();

        this.manager = new UserResponseModel(game.getManager());

        if (game.getWinner() != null) {
            this.winner = new PlayerResponse(game.getWinner());
        }

        if (game.getPlayers() != null) {
            this.players = game.getPlayers().stream()
                    .map(PlayerResponse::new)
                    .sorted(Comparator.comparing(PlayerResponse::getGender).reversed())
                    .collect(groupingBy(PlayerResponse::getDistrict));
        }

        if (game.getPlannedEvents() != null) {
            this.plannedEvents = game.getPlannedEvents().stream().map(PlannedEventResponse::new).toList();
        }
    }
}
