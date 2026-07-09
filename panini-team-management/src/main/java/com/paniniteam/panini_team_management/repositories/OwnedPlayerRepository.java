package com.paniniteam.panini_team_management.repositories;

import com.paniniteam.panini_team_management.entities.OwnedPlayer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OwnedPlayerRepository
        extends MongoRepository<OwnedPlayer, String> {

    Optional<OwnedPlayer> findByEmail(String email);
}