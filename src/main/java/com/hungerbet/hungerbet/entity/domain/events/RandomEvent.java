package com.hungerbet.hungerbet.entity.domain.events;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class RandomEvent {
    private UUID id;
    private String name;
    private String description;
    private Date dateStart;
    private UUID gameId;
    private boolean isHappened;
}
