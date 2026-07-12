package com.paninitorunaments.paninitorunaments.service.impl;

import com.paninitorunaments.paninitorunaments.entity.Championnat;
import com.paninitorunaments.paninitorunaments.entity.Standing;
import com.paninitorunaments.paninitorunaments.exception.TournamentNotFoundException;
import com.paninitorunaments.paninitorunaments.repository.ChampionnatRepository;
import com.paninitorunaments.paninitorunaments.repository.StandingRepository;
import com.paninitorunaments.paninitorunaments.service.TournamentWinnerService;
import com.paninitorunaments.paninitorunaments.service.UserStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TournamentWinnerServiceImpl
        implements TournamentWinnerService {

    private final ChampionnatRepository championnatRepository;

    private final StandingRepository standingRepository;

    private final UserStatisticsService userStatisticsService;

    @Override
    public void processTournamentWinner(
            Long tournamentId
    ) {

        Championnat championnat =
                championnatRepository
                        .findById(tournamentId)
                        .orElseThrow(
                                () ->
                                        new TournamentNotFoundException(
                                                tournamentId
                                        )
                        );

        boolean finished =
                championnat.getMatches()
                        .stream()
                        .allMatch(
                                match ->
                                        Boolean.TRUE.equals(
                                                match.getPlayed()
                                        )
                        );

        if (!finished) {
            return;
        }

        if (Boolean.TRUE.equals(
                championnat.getWinnerProcessed()
        )) {
            return;
        }

        Standing championStanding =
                standingRepository
                        .findByChampionnatIdOrderByPointsDescGoalDifferenceDescGoalsForDesc(
                                championnat.getId()
                        )
                        .stream()
                        .findFirst()
                        .orElseThrow();

        String winnerEmail =
                championStanding
                        .getTeam()
                        .getEmail();

        userStatisticsService
                .incrementTournamentWon(
                        winnerEmail
                );

        championnat.setWinnerProcessed(true);

        championnatRepository.save(championnat);
    }
}