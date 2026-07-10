package com.paninitorunaments.paninitorunaments.service;

import com.paninitorunaments.paninitorunaments.entity.Championnat;

import java.util.List;

public interface TournamentService {

    Championnat createTournament(String name);

    Championnat addTeams(Long tournamentId, List<Long> teamIds);

    Championnat getTournament(Long id);

    List<Championnat> getAll();
}