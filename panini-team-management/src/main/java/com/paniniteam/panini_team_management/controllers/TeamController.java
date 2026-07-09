package com.paniniteam.panini_team_management.controllers;

import com.paniniteam.panini_team_management.dto.TeamCreateRequest;
import com.paniniteam.panini_team_management.dto.TeamResponse;
import com.paniniteam.panini_team_management.services.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @PostMapping
    public ResponseEntity<TeamResponse> createTeam(
            @Valid @RequestBody TeamCreateRequest request) {

        TeamResponse response =
                teamService.createTeam(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<TeamResponse>> getTeams(
            @PathVariable String email) {

        return ResponseEntity.ok(
                teamService.getTeams(email)
        );
    }
}