package com.paninitorunaments.paninitorunaments.service.impl;

import com.paninitorunaments.paninitorunaments.dto.DashboardResponse;
import com.paninitorunaments.paninitorunaments.entity.UserStatistics;
import com.paninitorunaments.paninitorunaments.repository.ChampionnatRepository;
import com.paninitorunaments.paninitorunaments.repository.TeamRepository;
import com.paninitorunaments.paninitorunaments.service.DashboardService;
import com.paninitorunaments.paninitorunaments.service.UserStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl
        implements DashboardService {

    private final TeamRepository teamRepository;

    private final ChampionnatRepository championnatRepository;

    private final UserStatisticsService statisticsService;

    @Override
    public DashboardResponse getDashboard(
            String email
    ) {

        UserStatistics statistics =
                statisticsService.getByEmail(email);

        return DashboardResponse
                .builder()
                .teams(
                        (long) teamRepository
                                .findByEmail(email)
                                .size()
                )
                .tournaments(
                        (long) championnatRepository
                                .findByEmail(email)
                                .size()
                )
                .tournamentsWon(
                        statistics.getTournamentsWon()
                )
                .matchesPlayed(
                        statistics.getMatchesPlayed()
                )
                .matchesWon(
                        statistics.getMatchesWon()
                )
                .goalsScored(
                        statistics.getGoalsScored()
                )
                .build();
    }
}