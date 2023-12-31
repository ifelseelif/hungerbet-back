package com.hungerbet.hungerbet.entity.domain;

import com.hungerbet.hungerbet.entity.exceptions.HttpException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
public class Game {
    @Id
    private UUID id;
    private String name;
    @Enumerated(EnumType.ORDINAL)
    private GameStatus status;
    private Date dateStart;
    private Date dateEnd;
    private String description;
    private String arenaDescription;
    private String arenaType;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Player winner;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Player> players;

    @OneToMany(fetch = FetchType.EAGER)
    private List<PlannedEvent> plannedEvents;

    @OneToMany(fetch = FetchType.EAGER)
    private List<HappenedEvent> happenedEvents;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Item> items;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    public Game(String name,
                GameStatus gameStatus,
                Date startDate, String arenaDescription,
                String description, String arenaType, User manager) {
        this.name = name;
        this.dateStart = startDate;
        this.description = description;
        this.arenaDescription = arenaDescription;
        this.arenaType = arenaType;
        this.manager = manager;

        this.status = gameStatus;

        this.description = description;
        this.players = new ArrayList<>();
        this.happenedEvents = new ArrayList<>();
        this.plannedEvents = new ArrayList<>();
        this.items = new ArrayList<>();

        this.id = UUID.randomUUID();
    }

    public void publish() throws HttpException {
        if (name.isBlank()) {
            throw new HttpException("Game name is blank", HttpStatus.BAD_REQUEST);
        }

        if (description.isBlank()) {
            throw new HttpException("Game description is blank", HttpStatus.BAD_REQUEST);
        }

        if (arenaType.isBlank()) {
            throw new HttpException("Game arenaType is blank", HttpStatus.BAD_REQUEST);
        }

        if (arenaDescription.isBlank()) {
            throw new HttpException("Game arenaDescription is blank", HttpStatus.BAD_REQUEST);
        }

        Date currentDate = new Date();
        if (dateStart.before(currentDate)) {
            throw new HttpException("Start of game in past", HttpStatus.BAD_REQUEST);
        }

        this.status = GameStatus.planned;
    }

    public void start() throws HttpException {
        if (players.stream().count() == 24) {
            throw new HttpException("Count of participant is not 24", HttpStatus.BAD_REQUEST);
        }

        status = GameStatus.ongoing;

        if (IsFinish()) {
            this.Finish();
        }
    }

    public boolean IsFinish() {
        return players
                .stream()
                .filter(participant -> !participant.isDead())
                .toList()
                .size() == 1;
    }

    public void Finish() throws HttpException {
        this.winner = players
                .stream()
                .filter(participant -> !participant.isDead())
                .findFirst()
                .orElseThrow(() -> new HttpException("Not found winner, no one alive", HttpStatus.BAD_REQUEST));

        this.status = GameStatus.completed;
    }

    public void attachPlayer(Player newParticipant) throws HttpException {
        if (players.stream().anyMatch(participant -> participant.getId() == newParticipant.getId())) {
            throw new HttpException("Participant already added", HttpStatus.BAD_REQUEST);
        }

        players.add(newParticipant);
    }

    public void addHappenedEvent(HappenedEvent happenedEvent) {
        if (IsFinish() && happenedEvent.getHappenedEventType() != HappenedEventType.other) {
            return;
        }

        happenedEvents.add(happenedEvent);
    }

    public void addPlannedEvent(PlannedEvent plannedEvent) {
        this.plannedEvents.add(plannedEvent);
    }
}
