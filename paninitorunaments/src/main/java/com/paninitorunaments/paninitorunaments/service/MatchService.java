package com.paninitorunaments.paninitorunaments.service;

import com.paninitorunaments.paninitorunaments.dto.MatchResultRequest;
import com.paninitorunaments.paninitorunaments.entity.Match;

public interface MatchService {

    Match saveResult(
            Long matchId,
            MatchResultRequest request
    );

    void startMatch(Long matchId);
}