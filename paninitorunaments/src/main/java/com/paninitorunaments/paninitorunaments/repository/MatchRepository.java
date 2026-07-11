package com.paninitorunaments.paninitorunaments.repository;

import com.paninitorunaments.paninitorunaments.entity.Match;
import com.paninitorunaments.paninitorunaments.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Long> {

    List<Match> findByJourney(Integer journey);

    List<Match> findByJourneyAndPlayedFalse(Integer journey);


}