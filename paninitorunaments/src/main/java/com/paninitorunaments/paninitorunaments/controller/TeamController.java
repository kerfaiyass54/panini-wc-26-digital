package com.paninitorunaments.paninitorunaments.controller;

import com.paninitorunaments.paninitorunaments.dto.TeamRankingDto;
import com.paninitorunaments.paninitorunaments.entity.Team;
import com.paninitorunaments.paninitorunaments.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<List<Team>> getAll() {

        return ResponseEntity.ok(
                teamService.getAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                teamService.getById(id)
        );
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<List<Team>> getByEmail(
            @PathVariable String email
    ) {

        return ResponseEntity.ok(
                teamService.getByEmail(email)
        );
    }

    @GetMapping("/rankings")
    public ResponseEntity<List<TeamRankingDto>>
    getRankings() {

        return ResponseEntity.ok(
                teamService.getRankings()
        );
    }
}