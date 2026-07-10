package com.paninitorunaments.paninitorunaments.service.impl;

import com.paninitorunaments.paninitorunaments.dto.MatchRequest;
import com.paninitorunaments.paninitorunaments.dto.MatchResultRequest;
import com.paninitorunaments.paninitorunaments.entity.Championnat;
import com.paninitorunaments.paninitorunaments.entity.Match;
import com.paninitorunaments.paninitorunaments.entity.Standing;
import com.paninitorunaments.paninitorunaments.exception.MatchNotFoundException;
import com.paninitorunaments.paninitorunaments.kafka.MatchProducer;
import com.paninitorunaments.paninitorunaments.repository.ChampionnatRepository;
import com.paninitorunaments.paninitorunaments.repository.MatchRepository;
import com.paninitorunaments.paninitorunaments.repository.StandingRepository;
import com.paninitorunaments.paninitorunaments.service.MatchService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl
        implements MatchService {

    private final MatchRepository matchRepository;
    private final StandingRepository standingRepository;
    private final ChampionnatRepository championnatRepository;
    private final MatchProducer matchProducer;

    @Override
    public void startMatch(Long matchId) {

        Match match =
                matchRepository.findById(matchId)
                        .orElseThrow(
                                () -> new MatchNotFoundException(matchId)
                        );

        Championnat championnat =
                championnatRepository
                        .findByMatchesContains(match)
                        .orElseThrow();

        MatchRequest request =
                MatchRequest.builder()
                        .matchId(match.getId())
                        .tournamentId(championnat.getId())
                        .homeTeam(match.getTeam1().getName())
                        .awayTeam(match.getTeam2().getName())
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