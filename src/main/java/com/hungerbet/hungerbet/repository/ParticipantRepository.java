package com.hungerbet.hungerbet.repository;

import com.hungerbet.hungerbet.entity.domain.Game;
import com.hungerbet.hungerbet.entity.domain.GameStatus;
import com.hungerbet.hungerbet.entity.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
    @Query("SELECT p FROM Participant p WHERE p.age = ?3 and p.firstName = ?1 and p.lastName = ?2 and p.gender = ?4")
    Optional<Participant> findParticipant(String firstName, String last_name, int age, int gender);
}
