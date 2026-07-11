package com.paninitorunaments.paninitorunaments.kafka;

import com.paninitorunaments.paninitorunaments.dto.GeneratedPlayer;
import com.paninitorunaments.paninitorunaments.dto.GeneratedTeamMessage;
import com.paninitorunaments.paninitorunaments.entity.Player;
import com.paninitorunaments.paninitorunaments.entity.Team;
import com.paninitorunaments.paninitorunaments.repository.PlayerRepository;
import com.paninitorunaments.paninitorunaments.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeneratedTeamConsumer {

    private final TeamRepository teamRepository;

    private final PlayerRepository playerRepository;

    @KafkaListener(
            topics = "generated-team-topic",
            groupId = "generated-team-group"
    )
    public void consume(
            GeneratedTeamMessage message
    ) {

        log.info(
                "Generated team received: {}",
                message.getTeamName()
        );

        List<Player> players =
                message.getPlayers()
                        .stream()
                        .map(
                                GeneratedPlayer::getId
                        )
                        .map(id ->
                                playerRepository
                                        .findById(id)
                                        .orElseThrow()
                        )
                        .toList();

        Team team =
                Team.builder()
                        .name(
                                message.getTeamName()
                        )
                        .email(
                                message.getEmail()
                        )
                        .dateCreate(
                                LocalDateTime.now()
                        )
                        .players(players)
                        .build();

        teamRepository.save(team);

        log.info(
                "Team {} saved successfully",
                team.getName()
        );
    }
}