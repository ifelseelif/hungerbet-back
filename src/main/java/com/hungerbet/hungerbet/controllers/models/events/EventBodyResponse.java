package com.hungerbet.hungerbet.controllers.models.events;

import com.hungerbet.hungerbet.controllers.models.item.ItemResponse;
import com.hungerbet.hungerbet.controllers.models.player.PlayerResponse;
import com.hungerbet.hungerbet.entity.domain.Player;
import com.hungerbet.hungerbet.entity.domain.PlayerState;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventBodyResponse {
    private PlayerResponse player;
    private String degree;
    private String name;
    private String text;
    private ItemResponse item;

    public static EventBodyResponse CreateOtherEvent(Player player, String text) {
        EventBodyResponse eventBodyResponse = new EventBodyResponse();
        if (player != null) {
            eventBodyResponse.player = new PlayerResponse(player);
        }
        eventBodyResponse.text = text;
        return eventBodyResponse;
    }

    public static EventBodyResponse CreateRandomEvent(String name) {
        EventBodyResponse eventBodyResponse = new EventBodyResponse();
        eventBodyResponse.name = name;
        return eventBodyResponse;
    }

    public static EventBodyResponse CreatePlayerKillEvent(Player player) {
        EventBodyResponse eventBodyResponse = new EventBodyResponse();
        eventBodyResponse.player = new PlayerResponse(player);
        return eventBodyResponse;
    }

    public static EventBodyResponse CreatePlayerInjuryEvent(Player player, PlayerState playerState) {
        EventBodyResponse eventBodyResponse = new EventBodyResponse();
        eventBodyResponse.player = new PlayerResponse(player);
        eventBodyResponse.degree = playerState.toString();
        return eventBodyResponse;
    }


    public static EventBodyResponse CreateSupplyEvent(Player player, String item) {
        EventBodyResponse eventBodyResponse = new EventBodyResponse();
        eventBodyResponse.player = new PlayerResponse(player);
        eventBodyResponse.item = new ItemResponse(item);
        return eventBodyResponse;
    }
}
