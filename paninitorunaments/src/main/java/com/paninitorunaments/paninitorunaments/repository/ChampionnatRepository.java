package com.paninitorunaments.paninitorunaments.repository;

import com.paninitorunaments.paninitorunaments.entity.Championnat;
import com.paninitorunaments.paninitorunaments.entity.Match;
import com.paninitorunaments.paninitorunaments.entity.Standing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChampionnatRepository extends JpaRepository<Championnat, Long> {

    Optional<Championnat> findByMatchesContains(Match match);


    List<Championnat> findByEmail(
            String email
    );

}