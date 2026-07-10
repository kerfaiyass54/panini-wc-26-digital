package com.paninitorunaments.paninitorunaments.service.impl;



import com.paninitorunaments.paninitorunaments.entity.Championnat;
import com.paninitorunaments.paninitorunaments.entity.Match;
import com.paninitorunaments.paninitorunaments.entity.Standing;
import com.paninitorunaments.paninitorunaments.entity.Team;
import com.paninitorunaments.paninitorunaments.exception.TournamentNotFoundException;
import com.paninitorunaments.paninitorunaments.repository.ChampionnatRepository;
import com.paninitorunaments.paninitorunaments.repository.MatchRepository;
import com.paninitorunaments.paninitorunaments.repository.StandingRepository;
import com.paninitorunaments.paninitorunaments.repository.TeamRepository;
import com.paninitorunaments.paninitorunaments.service.TournamentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final ChampionnatRepository championnatRepository;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    private final StandingRepository standingRepository;

    @Override
    public List<Match> getTournamentMatches(Long tournamentId) {

        Championnat championnat =
                championnatRepository.findById(tournamentId)
                        .orElseThrow(
                                () -> new TournamentNotFoundException(tournamentId)
                        );

        return championnat.getMatches();
    }

    @Override
    @Transactional
    public Championnat initializeStandings(Long tournamentId) {

        Championnat championnat =
                championnatRepository.findById(tournamentId)
                        .orElseThrow(
                                () -> new TournamentNotFoundException(tournamentId)
                        );

        standingRepository.deleteAll(
                championnat.getStandings()
        );

        List<Standing> standings = new ArrayList<>();

        for (Team team : championnat.getTeams()) {

            Standing standing = Standing.builder()
                    .championnat(championnat)
                    .team(team)
                    .played(0)
                    .won(0)
                    .drawn(0)
                    .lost(0)
                    .goalsFor(0)
                    .goalsAgainst(0)
                    .goalDifference(0)
                    .points(0)
                    .build();

            standings.add(
                    standingRepository.save(standing)
            );
        }

        championnat.setStandings(standings);

        return championnatRepository.save(championnat);
    }

    @Override
    public List<Standing> getStandings(Long tournamentId) {

        return standingRepository
                .findByChampionnatIdOrderByPointsDescGoalDifferenceDescGoalsForDesc(
                        tournamentId
                );
    }

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
                        .orElseThrow(
                                () -> new TournamentNotFoundException(tournamentId)
                        );

        List<Team> teams = teamRepository.findAllById(teamIds);

        championnat.getTeams().clear();
        championnat.getTeams().addAll(teams);

        return championnatRepository.save(championnat);
    }

    @Override
    @Transactional
    public Championnat generateFixtures(Long tournamentId) {

        Championnat championnat =
                championnatRepository.findById(tournamentId)
                        .orElseThrow(
                                () -> new TournamentNotFoundException(tournamentId)
                        );

        List<Team> teams = championnat.getTeams();

        if (teams.size() < 2) {
            throw new IllegalStateException(
                    "At least two teams are required"
            );
        }

        List<Match> matches = new ArrayList<>();

        for (int i = 0; i < teams.size(); i++) {

            for (int j = i + 1; j < teams.size(); j++) {

                Match match = Match.builder()
                        .team1(teams.get(i))
                        .team2(teams.get(j))
                        .goalsHome(0)
                        .goalsAway(0)
                        .build();

                matches.add(matchRepository.save(match));
            }
        }

        championnat.getMatches().clear();
        championnat.getMatches().addAll(matches);

        return championnatRepository.save(championnat);
    }

    @Override
    public Championnat getTournament(Long id) {

        return championnatRepository.findById(id)
                .orElseThrow(
                        () -> new TournamentNotFoundException(id)
                );
    }

    @Override
    public List<Championnat> getAll() {

        return championnatRepository.findAll();
    }
}
