package com.paninitorunaments.paninitorunaments.service.impl;

import com.paninitorunaments.paninitorunaments.entity.Championnat;
import com.paninitorunaments.paninitorunaments.entity.Team;
import com.paninitorunaments.paninitorunaments.repository.ChampionnatRepository;
import com.paninitorunaments.paninitorunaments.repository.TeamRepository;
import com.paninitorunaments.paninitorunaments.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final ChampionnatRepository championnatRepository;
    private final TeamRepository teamRepository;

    @Override
    public Championnat createTournament(String name) {

        Championnat championnat = Championnat.builder()
                .tournament(name)
                .build();

        return championnatRepository.save(championnat);
    }

    @Override
    public Championnat addTeams(Long tournamentId, List<Long> teamIds) {

        Championnat championnat =
                championnatRepository.findById(tournamentId)
                        .orElseThrow(() -> new RuntimeException("Tournament not found"));

        List<Team> teams = teamRepository.findAllById(teamIds);

        championnat.getTeams().addAll(teams);

        return championnatRepository.save(championnat);
    }

    @Override
    public Championnat getTournament(Long id) {

        return championnatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tournament not found"));
    }

    @Override
    public List<Championnat> getAll() {

        return championnatRepository.findAll();
    }
}