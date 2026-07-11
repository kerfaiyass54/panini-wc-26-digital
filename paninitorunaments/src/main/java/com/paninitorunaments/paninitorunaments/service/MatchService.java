package com.paninitorunaments.paninitorunaments.service;

import com.paninitorunaments.paninitorunaments.dto.MatchResultRequest;
import com.paninitorunaments.paninitorunaments.dto.TopScorerResponse;
import com.paninitorunaments.paninitorunaments.entity.Goal;
import com.paninitorunaments.paninitorunaments.entity.Match;

import java.util.List;

public interface MatchService {

    Match saveResult(
            Long matchId,
            MatchResultRequest request
    );

    void startMatch(Long matchId);

    List<Match> getTournamentMatches(Long tournamentId);

    Match getMatch(Long matchId);

    List<Goal> getMatchGoals(Long matchId);

    List<TopScorerResponse> getTopScorers();


}