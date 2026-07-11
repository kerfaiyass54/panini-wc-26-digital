package com.paninitorunaments.paninitorunaments.repository;

import com.paninitorunaments.paninitorunaments.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository
        extends JpaRepository<Player, Long> {

    Optional<Player> findByName(String name);

    List<Player> findByNameIn(
            List<String> names
    );
}