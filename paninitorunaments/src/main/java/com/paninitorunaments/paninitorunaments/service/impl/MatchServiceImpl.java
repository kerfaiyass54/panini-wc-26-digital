package com.paninitorunaments.paninitorunaments.service.impl;

import com.paninitorunaments.paninitorunaments.dto.*;
import com.paninitorunaments.paninitorunaments.entity.*;
import com.paninitorunaments.paninitorunaments.exception.MatchNotFoundException;
import com.paninitorunaments.paninitorunaments.exception.TournamentNotFoundException;
import com.paninitorunaments.paninitorunaments.kafka.MatchProducer;
import com.paninitorunaments.paninitorunaments.repository.ChampionnatRepository;
import com.paninitorunaments.paninitorunaments.repository.GoalRepository;
import com.paninitorunaments.paninitorunaments.repository.MatchRepository;
import com.paninitorunaments.paninitorunaments.repository.StandingRepository;
import com.paninitorunaments.paninitorunaments.service.MatchService;
import com.paninitorunaments.paninitorunaments.service.TournamentService;
import com.paninitorunaments.paninitorunaments.service.UserStatisticsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl
        implements MatchService {

    private final MatchRepository matchRepository;
    private final StandingRepository standingRepository;
    private final ChampionnatRepository championnatRepository;
    private final MatchProducer matchProducer;
    private final GoalRepository goalRepository;
    private final UserStatisticsService userStatisticsService;
    private final TournamentService tournamentService;

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
    public List<PlayerStatisticsDto>
    getPlayerStatistics() {

        return goalRepository
                .getPlayerStatistics()
                .stream()
                .map(row ->
                        PlayerStatisticsDto
                                .builder()
                                .player(
                                        (String) row[0]
                                )
                                .goals(
                                        (Long) row[1]
                                )
                                .build()
                )
                .toList();
    }

    @Override
    public Match getMatch(Long matchId) {

        return matchRepository.findById(matchId)
                .orElseThrow(
                        () -> new MatchNotFoundException(matchId)
                );
    }

    @Override
    public List<Goal> getMatchGoals(Long matchId) {

        return goalRepository.findByMatchId(matchId);
    }

    @Override
    public List<TopScorerResponse> getTopScorers() {

        return goalRepository.getTopScorers()
                .stream()
                .map(result ->
                        TopScorerResponse.builder()
                                .player((String) result[0])
                                .goals((Long) result[1])
                                .build()
                )
                .toList();
    }

    private TeamDto mapTeam(Team team) {

        return TeamDto.builder()
                .name(team.getName())
                .players(
                        team.getPlayers()
                                .stream()
                                .map(player ->
                                        PlayerDto.builder()
                                                .id(player.getId())
                                                .name(player.getName())
                                                .ability(player.getAbility())
                                                .position(player.getPosition())
                                                .build()
                                )
                                .toList()
                )
                .build();
    }

    @Override
    public void startMatch(Long matchId) {

        Match match =
                matchRepository.findById(matchId)
                        .orElseThrow(
                                () -> new MatchNotFoundException(matchId)
                        );

        if (Boolean.TRUE.equals(match.getPlayed())) {
            throw new IllegalStateException(
                    "Match already played"
            );
        }

        MatchRequest request =
                MatchRequest.builder()
                        .matchId(match.getId())
                        .homeTeam(
                                mapTeam(
                                        match.getTeam1()
                                )
                        )
                        .awayTeam(
                                mapTeam(
                                        match.getTeam2()
                                )
                        )
                        .build();

        matchProducer.sendMatch(request);
    }

    @Override
    @Transactional
    public Match saveResult(
            Long matchId,
            MatchResultRequest request
    ) {

        Match match =
                matchRepository.findById(matchId)
                        .orElseThrow(
                                () -> new MatchNotFoundException(matchId)
                        );

        match.setGoalsHome(
                request.getGoalsHome()
        );

        match.setGoalsAway(
                request.getGoalsAway()
        );

        matchRepository.save(match);

        String homeEmail =
                match.getTeam1().getEmail();

        String awayEmail =
                match.getTeam2().getEmail();

        userStatisticsService.incrementMatchPlayed(
                homeEmail
        );

        userStatisticsService.incrementMatchPlayed(
                awayEmail
        );

        userStatisticsService.addGoals(
                homeEmail,
                request.getGoalsHome()
        );

        userStatisticsService.addGoals(
                awayEmail,
                request.getGoalsAway()
        );

        if (request.getGoalsHome()
                > request.getGoalsAway()) {

            userStatisticsService.incrementMatchWon(
                    homeEmail
            );
        }

        if (request.getGoalsAway()
                > request.getGoalsHome()) {

            userStatisticsService.incrementMatchWon(
                    awayEmail
            );
        }

        Championnat championnat =
                championnatRepository
                        .findByMatchesContains(match)
                        .orElseThrow();

        Standing homeStanding =
                standingRepository
                        .findByChampionnatAndTeam(
                                championnat,
                                match.getTeam1()
                        )
                        .orElseThrow();

        Standing awayStanding =
                standingRepository
                        .findByChampionnatAndTeam(
                                championnat,
                                match.getTeam2()
                        )
                        .orElseThrow();

        updateStandings(
                homeStanding,
                awayStanding,
                request.getGoalsHome(),
                request.getGoalsAway()
        );

        standingRepository.save(homeStanding);
        standingRepository.save(awayStanding);

        tournamentService.processTournamentWinner(
                championnat.getId()
        );

        return match;
    }

    private void updateStandings(
            Standing home,
            Standing away,
            int goalsHome,
            int goalsAway
    ) {

        home.setPlayed(home.getPlayed() + 1);
        away.setPlayed(away.getPlayed() + 1);

        home.setGoalsFor(
                home.getGoalsFor() + goalsHome
        );

        home.setGoalsAgainst(
                home.getGoalsAgainst() + goalsAway
        );

        away.setGoalsFor(
                away.getGoalsFor() + goalsAway
        );

        away.setGoalsAgainst(
                away.getGoalsAgainst() + goalsHome
        );

        home.setGoalDifference(
                home.getGoalsFor()
                        - home.getGoalsAgainst()
        );

        away.setGoalDifference(
                away.getGoalsFor()
                        - away.getGoalsAgainst()
        );

        if (goalsHome > goalsAway) {

            home.setWon(home.getWon() + 1);
            home.setPoints(home.getPoints() + 3);

            away.setLost(away.getLost() + 1);

        } else if (goalsAway > goalsHome) {

            away.setWon(away.getWon() + 1);
            away.setPoints(away.getPoints() + 3);

            home.setLost(home.getLost() + 1);

        } else {

            home.setDrawn(home.getDrawn() + 1);
            away.setDrawn(away.getDrawn() + 1);

            home.setPoints(home.getPoints() + 1);
            away.setPoints(away.getPoints() + 1);
        }
    }
}