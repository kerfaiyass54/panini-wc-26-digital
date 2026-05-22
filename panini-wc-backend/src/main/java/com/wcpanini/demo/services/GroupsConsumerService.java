package com.wcpanini.demo.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wcpanini.demo.dtos.StatsDTO;
import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupsConsumerService
        implements ConsumerSeekAware {

    private final ObjectMapper objectMapper =
            new ObjectMapper();

    private Map<String, Integer> groups =
            new HashMap<>();

    @KafkaListener(
            topics = "stickers-groups-topic",
            groupId = "demo-group"
    )
    public void consume(
            String message
    ) {

        try {

            Map<String, Integer> data =
                    objectMapper.readValue(
                            message,
                            new TypeReference<>() {}
                    );

            System.out.println(
                    "GROUPS : " + data
            );

            groups = data;

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    // LOAD LAST MESSAGE ON STARTUP
    @Override
    public void onPartitionsAssigned(
            Map<TopicPartition, Long> assignments,
            ConsumerSeekCallback callback
    ) {

        assignments.forEach(
                (topicPartition, offset) -> {

                    callback.seekRelative(
                            topicPartition.topic(),
                            topicPartition.partition(),
                            -1,
                            true
                    );
                }
        );
    }

    // RAW
    public Map<String, Integer> getRaw() {

        return groups;
    }

    // CLEAN
    public List<StatsDTO> getGroups() {

        return groups.entrySet()
                .stream()
                .map(entry ->
                        new StatsDTO(
                                entry.getKey(),
                                entry.getValue()
                        )
                )
                .sorted(
                        Comparator.comparing(
                                StatsDTO::getTotal
                        ).reversed()
                )
                .collect(Collectors.toList());
    }
}