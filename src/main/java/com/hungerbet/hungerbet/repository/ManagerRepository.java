package com.hungerbet.hungerbet.repository;

import com.hungerbet.hungerbet.entity.domain.Manager;
import com.hungerbet.hungerbet.entity.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, UUID> {
}
