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

    @Embedded
    private EventBody body;

    public HappenedEvent(Date happenedTime, HappenedEventType happenedEventType, EventBody body) {
        this.happenedTime = happenedTime;
        this.happenedEventType = happenedEventType;
        this.body = body;
    }
}
