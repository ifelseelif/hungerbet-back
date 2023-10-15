package com.hungerbet.hungerbet.entity.domain.events;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class PlayerEvent {
    private UUID playerId;
}
