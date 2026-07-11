package com.paninitorunaments.paninitorunaments.kafka;

import com.paninitorunaments.paninitorunaments.dto.GoalScorerDto;
import com.paninitorunaments.paninitorunaments.dto.MatchResultMessage;
import com.paninitorunaments.paninitorunaments.dto.MatchResultRequest;
import com.paninitorunaments.paninitorunaments.entity.Goal;
import com.paninitorunaments.paninitorunaments.entity.Match;
import com.paninitorunaments.paninitorunaments.entity.Player;
import com.paninitorunaments.paninitorunaments.repository.GoalRepository;
import com.paninitorunaments.paninitorunaments.repository.MatchRepository;
import com.paninitorunaments.paninitorunaments.repository.PlayerRepository;
import com.paninitorunaments.paninitorunaments.service.MatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchResultConsumer {

    private final MatchService matchService;
    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final GoalRepository goalRepository;

    @KafkaListener(
            topics = "${app.kafka.match-result-topic}",
            groupId = "tournament-group"
    )
    public void consume(MatchResultMessage message) {

        log.info(
                "Received result for match {} => {}:{}",
                message.getMatchId(),
                message.getGoalsHome(),
                message.getGoalsAway()
        );

        Match match = matchRepository.findById(
                message.getMatchId()
        ).orElseThrow(() ->
                new RuntimeException(
                        "Match not found: " + message.getMatchId()
                )
        );

        if (Boolean.TRUE.equals(match.getPlayed())) {
            log.warn(
                    "Match {} already processed",
                    match.getId()
            );
            return;
        }

        MatchResultRequest request =
                new MatchResultRequest();

        request.setGoalsHome(
                message.getGoalsHome()
        );

        request.setGoalsAway(
                message.getGoalsAway()
        );

        matchService.saveResult(
                match.getId(),
                request
        );

        if (message.getScorers() != null) {

            for (GoalScorerDto scorer : message.getScorers()) {

                        Player player =
                        playerRepository.findById(
                                scorer.getPlayerId()
                        ).orElseThrow();

                Goal goal =
                        Goal.builder()
                                .minute(
                                        scorer.getMinute()
                                )
                                .player(player)
                                .match(match)
                                .build();

                goalRepository.save(goal);
            }
        }

        match.setPlayed(true);

        matchRepository.save(match);

        log.info(
                "Match {} successfully processed",
                match.getId()
        );
    }
}