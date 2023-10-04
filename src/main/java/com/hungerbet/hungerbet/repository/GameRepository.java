package com.hungerbet.hungerbet.repository;

import com.hungerbet.hungerbet.entity.domain.Game;
import com.hungerbet.hungerbet.entity.domain.GameStatus;
import com.hungerbet.hungerbet.entity.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {
    @Query("SELECT g FROM Game g WHERE g.status IN :publishedGameStatuses")
    List<Game> findPublishedGames(List<GameStatus> publishedGameStatuses);
    Optional<Game> findByName(String name);
}
