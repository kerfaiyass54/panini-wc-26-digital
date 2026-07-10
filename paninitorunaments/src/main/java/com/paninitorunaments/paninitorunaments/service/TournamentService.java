package com.paninitorunaments.paninitorunaments.service;

import com.paninitorunaments.paninitorunaments.entity.Championnat;
import com.paninitorunaments.paninitorunaments.entity.Standing;

import java.util.List;

public interface TournamentService {

    Championnat createTournament(String name);

    Championnat addTeams(Long tournamentId, List<Long> teamIds);

    Championnat generateFixtures(Long tournamentId);

    Championnat getTournament(Long id);

    List<Championnat> getAll();

    Championnat initializeStandings(Long tournamentId);

    List<Standing> getStandings(Long tournamentId);
}