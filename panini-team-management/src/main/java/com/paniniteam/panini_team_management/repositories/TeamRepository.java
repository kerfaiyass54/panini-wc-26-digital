package com.paniniteam.panini_team_management.repositories;

import com.paniniteam.panini_team_management.entities.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepository extends MongoRepository<Team, String> {
}