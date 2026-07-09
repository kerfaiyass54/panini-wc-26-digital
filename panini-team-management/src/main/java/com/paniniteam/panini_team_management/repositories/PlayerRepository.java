package com.paniniteam.panini_team_management.repositories;

import com.paniniteam.panini_team_management.entities.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository
        extends MongoRepository<Player, String> {

    Optional<Player> findByCode(String code);

    List<Player> findByCodeIn(List<String> codes);
}