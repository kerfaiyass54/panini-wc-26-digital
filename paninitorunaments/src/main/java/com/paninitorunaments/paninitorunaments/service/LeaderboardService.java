package com.paninitorunaments.paninitorunaments.service;

import com.paninitorunaments.paninitorunaments.dto.LeaderboardDto;

import java.util.List;

public interface LeaderboardService {

    List<LeaderboardDto> getLeaderboard();
}