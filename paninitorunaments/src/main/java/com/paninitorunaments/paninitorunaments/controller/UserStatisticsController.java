package com.paninitorunaments.paninitorunaments.controller;


import com.paninitorunaments.paninitorunaments.entity.UserStatistics;
import com.paninitorunaments.paninitorunaments.service.UserStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class UserStatisticsController {

    private final UserStatisticsService service;

    @GetMapping("/{email}")
    public ResponseEntity<UserStatistics> getStatistics(
            @PathVariable String email
    ) {

        return ResponseEntity.ok(
                service.getByEmail(email)
        );
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<UserStatistics>>
    getLeaderboard() {

        return ResponseEntity.ok(
                service.getLeaderboard()
        );
    }
}