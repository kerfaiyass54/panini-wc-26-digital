package com.paninitorunaments.paninitorunaments.kafka;

import com.paninitorunaments.paninitorunaments.dto.OwnedPlayersRequest;
import com.paninitorunaments.paninitorunaments.dto.OwnedPlayersResponse;
import com.paninitorunaments.paninitorunaments.dto.PlayerInfo;
import com.paninitorunaments.paninitorunaments.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OwnedPlayersConsumer {

    private final PlayerRepository playerRepository;

    private final OwnedPlayersResponseProducer producer;

    @KafkaListener(
            topics = "owned-players-request-topic",
            groupId = "tournament-group"
    )
    public void consume(
            OwnedPlayersRequest request
    ) {

        List<PlayerInfo> players =
                playerRepository
                        .findByNameIn(
                                request.getPlayerNames()
                        )
                        .stream()
                        .map(player ->
                                PlayerInfo.builder()
                                        .id(player.getId())
                                        .name(player.getName())
                                        .position(
                                                player.getPosition()
                                        )
                                        .ability(
                                                player.getAbility()
                                        )
                                        .nationality(
                                                player.getNationality()
                                        )
                                        .build()
                        )
                        .toList();

        producer.send(
                OwnedPlayersResponse.builder()
                        .email(
                                request.getEmail()
                        )
                        .players(players)
                        .build()
        );
    }
}