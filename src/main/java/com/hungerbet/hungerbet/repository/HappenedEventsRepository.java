package com.hungerbet.hungerbet.repository;

import com.hungerbet.hungerbet.entity.domain.HappenedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HappenedEventsRepository extends JpaRepository<HappenedEvent, UUID> {
}
