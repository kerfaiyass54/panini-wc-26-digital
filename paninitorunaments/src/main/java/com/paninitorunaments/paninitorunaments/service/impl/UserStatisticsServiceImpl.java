package com.paninitorunaments.paninitorunaments.service.impl;

import com.paninitorunaments.paninitorunaments.entity.UserStatistics;
import com.paninitorunaments.paninitorunaments.repository.UserStatisticsRepository;
import com.paninitorunaments.paninitorunaments.service.UserStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserStatisticsServiceImpl
        implements UserStatisticsService {

    private final UserStatisticsRepository repository;

    private UserStatistics getOrCreate(
            String email
    ) {

        return repository.findByEmail(email)
                .orElseGet(() ->
                        repository.save(
                                UserStatistics.builder()
                                        .email(email)
                                        .tournamentsPlayed(0)
                                        .tournamentsWon(0)
                                        .matchesPlayed(0)
                                        .matchesWon(0)
                                        .goalsScored(0)
                                        .build()
                        )
                );
    }

    @Override
    public UserStatistics getByEmail(
            String email
    ) {

        return getOrCreate(email);
    }

    @Override
    public void incrementTournamentPlayed(
            String email
    ) {

        UserStatistics stats =
                getOrCreate(email);

        stats.setTournamentsPlayed(
                stats.getTournamentsPlayed() + 1
        );

        repository.save(stats);
    }

    @Override
    public void incrementTournamentWon(
            String email
    ) {

        UserStatistics stats =
                getOrCreate(email);

        stats.setTournamentsWon(
                stats.getTournamentsWon() + 1
        );

        repository.save(stats);
    }

    @Override
    public void incrementMatchPlayed(
            String email
    ) {

        UserStatistics stats =
                getOrCreate(email);

        stats.setMatchesPlayed(
                stats.getMatchesPlayed() + 1
        );

        repository.save(stats);
    }

    @Override
    public void incrementMatchWon(
            String email
    ) {

        UserStatistics stats =
                getOrCreate(email);

        stats.setMatchesWon(
                stats.getMatchesWon() + 1
        );

        repository.save(stats);
    }

    @Override
    public List<UserStatistics>
    getLeaderboard() {

        return repository
                .findAllByOrderByTournamentsWonDesc();
    }

    @Override
    public void addGoals(
            String email,
            int goals
    ) {

        UserStatistics stats =
                getOrCreate(email);

        stats.setGoalsScored(
                stats.getGoalsScored() + goals
        );

        repository.save(stats);
    }
}