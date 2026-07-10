package com.paninitorunaments.paninitorunaments.repository;

import com.paninitorunaments.paninitorunaments.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository
        extends JpaRepository<Player, Long> {

    Optional<Player> findByName(String name);
}