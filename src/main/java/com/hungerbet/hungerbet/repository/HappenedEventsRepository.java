package com.hungerbet.hungerbet.repository;

import com.hungerbet.hungerbet.entity.domain.HappenedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HappenedEventsRepository extends JpaRepository<HappenedEvent, UUID> {
}
