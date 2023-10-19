package com.hungerbet.hungerbet.entity.domain;

import jakarta.annotation.Nullable;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@Embeddable
public class EventBody {
    @Nullable
    private UUID playerId;
    @Nullable
    private PlayerState playerState;
    @Nullable
    private String text;
    @Nullable
    private UUID plannedEventId;
    @Nullable
    private UUID itemId;

    public static EventBody CreateOtherEvent(UUID playerId, String text) {
        EventBody eventBody = new EventBody();

        eventBody.text = text;
        eventBody.playerId = playerId;

        return eventBody;
    }
}
