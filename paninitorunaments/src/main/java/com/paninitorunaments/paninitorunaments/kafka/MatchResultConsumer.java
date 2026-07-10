package com.paninitorunaments.paninitorunaments.kafka;

import com.paninitorunaments.paninitorunaments.dto.MatchResultMessage;
import com.paninitorunaments.paninitorunaments.dto.MatchResultRequest;
import com.paninitorunaments.paninitorunaments.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchResultConsumer {

    private final MatchService matchService;

    @KafkaListener(
            topics = "${app.kafka.match-result-topic}",
            groupId = "tournament-group"
    )
    public void consume(
            MatchResultMessage message
    ) {

        MatchResultRequest request =
                new MatchResultRequest();

        request.setGoalsHome(
                message.getGoalsHome()
        );

        request.setGoalsAway(
                message.getGoalsAway()
        );

        matchService.saveResult(
                message.getMatchId(),
                request
        );
    }
}