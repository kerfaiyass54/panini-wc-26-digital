package com.paninitorunaments.paninitorunaments.service;

import com.paninitorunaments.paninitorunaments.entity.UserStatistics;

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

    void addGoals(
            String email,
            int goals
    );
}