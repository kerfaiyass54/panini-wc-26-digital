package com.paninitorunaments.paninitorunaments.service.impl;

import com.paninitorunaments.paninitorunaments.dto.LeaderboardDto;
import com.paninitorunaments.paninitorunaments.repository.UserStatisticsRepository;
import com.paninitorunaments.paninitorunaments.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaderboardServiceImpl
        implements LeaderboardService {

    private final UserStatisticsRepository repository;

    @Override
    public List<LeaderboardDto> getLeaderboard() {

        return repository.findAll()
                .stream()
                .map(statistics -> {

                    int score =
                            statistics.getTournamentsWon() * 100
                                    + statistics.getMatchesWon() * 10
                                    + statistics.getGoalsScored();

                    return LeaderboardDto
                            .builder()
                            .email(
                                    statistics.getEmail()
                            )
                            .tournamentsWon(
                                    statistics.getTournamentsWon()
                            )
                            .matchesWon(
                                    statistics.getMatchesWon()
                            )
                            .goalsScored(
                                    statistics.getGoalsScored()
                            )
                            .score(score)
                            .build();
                })
                .sorted(
                        Comparator.comparing(
                                LeaderboardDto::getScore
                        ).reversed()
                )
                .toList();
    }
}