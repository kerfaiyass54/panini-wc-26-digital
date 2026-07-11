package com.paninitorunaments.paninitorunaments.controller;

import com.paninitorunaments.paninitorunaments.dto.LeaderboardDto;
import com.paninitorunaments.paninitorunaments.service.LeaderboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @GetMapping
    public ResponseEntity<List<LeaderboardDto>>
    getLeaderboard() {

        return ResponseEntity.ok(
                leaderboardService.getLeaderboard()
        );
    }
}