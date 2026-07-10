package com.paninitorunaments.paninitorunaments.kafka;

import com.paninitorunaments.paninitorunaments.dto.MatchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.match-request-topic}")
    private String topic;

    public void sendMatch(MatchRequest request) {

        kafkaTemplate.send(
                topic,
                request.getMatchId().toString(),
                request
        );
    }
}