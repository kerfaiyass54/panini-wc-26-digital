package com.paninitorunaments.paninitorunaments.service;

import com.paninitorunaments.paninitorunaments.entity.UserStatistics;

import java.util.List;

public interface UserStatisticsService {

    UserStatistics getByEmail(
            String email
    );

    void incrementTournamentPlayed(
            String email
    );

    void incrementTournamentWon(
            String email
    );

    void incrementMatchPlayed(
            String email
    );

    void incrementMatchWon(
            String email
    );

    List<UserStatistics> getLeaderboard();

    void addGoals(
            String email,
            int goals
    );
}