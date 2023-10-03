package com.hungerbet.hungerbet.entity.domain;

import com.hungerbet.hungerbet.entity.exceptions.BadRequestException;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    @Enumerated(EnumType.STRING)
    private GameStatus status;
    private Date startDate;
    private Date endDate;
    private String arenaInfo;
    private String description;

    @OneToOne
    @JoinColumn(name = "winner_id")
    private Participant winner;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Participant> participants;

    @OneToMany
    private List<PlannedEvent> plannedEvents;

    @OneToMany(fetch = FetchType.EAGER)
    private List<HappenedEvent> happenedEvents;

    @OneToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    public Game(String name, GameStatus status,
                Date startDate, String arenaInfo,
                String description) {
        this.name = name;
        this.status = status;
        this.startDate = startDate;
        this.arenaInfo = arenaInfo;
        this.description = description;
        this.participants = new ArrayList<>();
        this.happenedEvents = new ArrayList<>();
        this.plannedEvents = new ArrayList<>();
    }

    public void publish() throws BadRequestException {
        if (name.isBlank()) {
            throw new BadRequestException("Game name is blank");
        }

        if (arenaInfo.isBlank()) {
            throw new BadRequestException("Game name is blank");
        }

        if (description.isBlank()) {
            throw new BadRequestException("Game name is blank");
        }

        Date currentDate = new Date();
        if (startDate.before(currentDate)) {
            throw new BadRequestException("Start of game in past");
        }

    }

    public void start() throws BadRequestException {
        if (participants.isEmpty()) {
            throw new BadRequestException("List of participant is empty");
        }

        status = GameStatus.ONGOING;

        if (IsFinish()) {
            this.Finish();
        }
    }

    public boolean IsFinish() {
        List<Participant> aliveParticipants = participants
                .stream()
                .filter(participant -> !participant.isAlive())
                .toList();

        return aliveParticipants.size() == 1;
    }

    public void Finish() throws BadRequestException {
        Participant aliveParticipant = participants
                .stream()
                .filter(participant -> !participant.isAlive())
                .findFirst()
                .orElseThrow(() -> new BadRequestException("Not found winner, no one alive"));

        this.winner = aliveParticipant;
        this.status = GameStatus.COMPLETED;
    }

    public void addParticipant(Participant newParticipant) throws BadRequestException {
        if (participants.stream().anyMatch(participant -> participant.getId() == newParticipant.getId())) {
            throw new BadRequestException("Participant already added");
        }

        participants.add(newParticipant);
    }

    public void addHappenedEvent(HappenedEvent happenedEvent) throws BadRequestException {
        if (IsFinish() && happenedEvent.getHappenedEventType() != HappenedEventType.OTHERS) {
            return;
        }
        switch (happenedEvent.getHappenedEventType()) {
            case HURTS ->
                    participants.stream().filter(participant -> participant.getId() == happenedEvent.getParticipantId()).forEach(participant -> participant.setState(ParticipantState.FLESH_WOUND));
            case KILLED ->
                    participants.stream().filter(participant -> participant.getId() == happenedEvent.getParticipantId()).forEach(participant -> participant.setState(ParticipantState.DEAD));
        }

        happenedEvents.add(happenedEvent);
    }

    public void addPlannedEvent(PlannedEvent plannedEvent) {
        this.plannedEvents.add(plannedEvent);
    }
}
