package com.paninitorunaments.paninitorunaments.service;

import com.paninitorunaments.paninitorunaments.dto.TeamRankingDto;
import com.paninitorunaments.paninitorunaments.entity.Team;

import java.util.List;

public interface TeamService {

    List<Team> getAll();

    Team getById(Long id);

    List<Team> getByEmail(String email);

    List<TeamRankingDto> getRankings();
}