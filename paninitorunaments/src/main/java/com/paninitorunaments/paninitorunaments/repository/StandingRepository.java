package com.paninitorunaments.paninitorunaments.repository;

import com.paninitorunaments.paninitorunaments.entity.Championnat;
import com.paninitorunaments.paninitorunaments.entity.Standing;
import com.paninitorunaments.paninitorunaments.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StandingRepository
        extends JpaRepository<Standing, Long> {

    List<Standing> findByChampionnatIdOrderByPointsDescGoalDifferenceDescGoalsForDesc(
            Long championnatId
    );

    Optional<Standing> findByChampionnatAndTeam(
            Championnat championnat,
            Team team
    );
}