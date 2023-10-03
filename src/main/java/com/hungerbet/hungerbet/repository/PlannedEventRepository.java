package com.hungerbet.hungerbet.repository;

import com.hungerbet.hungerbet.entity.domain.PlannedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlannedEventRepository extends JpaRepository<PlannedEvent, UUID> {
}
