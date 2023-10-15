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
    private Date happenedTime;

    @Enumerated(EnumType.STRING)
    private HappenedEventType happenedEventType;
    private String body;

    public HappenedEvent(Date happenedTime, HappenedEventType happenedEventType, String body) {
        this.happenedTime = happenedTime;
        this.happenedEventType = happenedEventType;
        this.body = body;
    }
}
