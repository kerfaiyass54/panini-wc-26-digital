package com.paniniteam.panini_team_management.repositories;

import com.paniniteam.panini_team_management.entities.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TeamRepository
        extends MongoRepository<Team, String> {

    List<Team> findByEmail(String email);
}