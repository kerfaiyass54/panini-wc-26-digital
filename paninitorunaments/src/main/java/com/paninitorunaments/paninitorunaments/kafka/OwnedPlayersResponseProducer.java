package com.paninitorunaments.paninitorunaments.kafka;


import com.paninitorunaments.paninitorunaments.dto.OwnedPlayersResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnedPlayersResponseProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(
            OwnedPlayersResponse response
    ) {

        kafkaTemplate.send(
                "owned-players-response-topic",
                response
        );
    }
}