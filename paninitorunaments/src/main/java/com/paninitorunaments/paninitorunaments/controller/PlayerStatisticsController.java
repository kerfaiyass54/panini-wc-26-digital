package com.paninitorunaments.paninitorunaments.controller;

import com.paninitorunaments.paninitorunaments.dto.PlayerStatisticsDto;
import com.paninitorunaments.paninitorunaments.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@RequiredArgsConstructor
public class PlayerStatisticsController {

    private final MatchService matchService;

    @GetMapping("/statistics")
    public ResponseEntity<List<PlayerStatisticsDto>>
    getStatistics() {

        return ResponseEntity.ok(
                matchService.getPlayerStatistics()
        );
    }
}