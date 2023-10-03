package com.hungerbet.hungerbet.entity.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
public class HappenedEvent {
    @Id
    @GeneratedValue
    private UUID id;
    private UUID participantId;
    private String name;
    private String description;
    private Date happenedTime;

    @Enumerated(EnumType.STRING)
    private HappenedEventType happenedEventType;

    public HappenedEvent(String name, String description, Date happenedTime, HappenedEventType happenedEventType) {
        this.name = name;
        this.description = description;
        this.happenedTime = happenedTime;
        this.happenedEventType = happenedEventType;
    }
}
