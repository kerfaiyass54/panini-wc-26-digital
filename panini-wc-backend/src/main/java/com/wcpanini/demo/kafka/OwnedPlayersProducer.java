package com.wcpanini.demo.kafka;

import com.wcpanini.demo.dtos.OwnedPlayersRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnedPlayersProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(
            OwnedPlayersRequest request
    ) {

        kafkaTemplate.send(
                "owned-players-request-topic",
                request
        );
    }
}