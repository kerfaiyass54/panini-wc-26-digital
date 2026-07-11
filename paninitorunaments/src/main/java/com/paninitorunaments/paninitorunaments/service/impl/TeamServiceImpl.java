package com.paninitorunaments.paninitorunaments.service.impl;

import com.paninitorunaments.paninitorunaments.dto.TeamRankingDto;
import com.paninitorunaments.paninitorunaments.entity.Team;
import com.paninitorunaments.paninitorunaments.repository.TeamRepository;
import com.paninitorunaments.paninitorunaments.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl
        implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    public List<Team> getAll() {

        return teamRepository.findAll();
    }

    @Override
    public Team getById(Long id) {

        return teamRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Team not found"
                        )
                );
    }

    @Override
    public List<Team> getByEmail(
            String email
    ) {

        return teamRepository.findByEmail(email);
    }

    @Override
    public List<TeamRankingDto> getRankings() {

        return teamRepository.findAll()
                .stream()
                .map(team -> {

                    double averageAbility =
                            team.getPlayers()
                                    .stream()
                                    .mapToInt(
                                            player ->
                                                    player.getAbility()
                                    )
                                    .average()
                                    .orElse(0);

                    return TeamRankingDto
                            .builder()
                            .id(
                                    team.getId()
                            )
                            .name(
                                    team.getName()
                            )
                            .email(
                                    team.getEmail()
                            )
                            .averageAbility(
                                    averageAbility
                            )
                            .playersCount(
                                    team.getPlayers().size()
                            )
                            .build();
                })
                .sorted(
                        Comparator.comparing(
                                TeamRankingDto::getAverageAbility
                        ).reversed()
                )
                .toList();
    }
}