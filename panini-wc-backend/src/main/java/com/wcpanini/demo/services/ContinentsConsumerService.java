package com.wcpanini.demo.services;

import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Service
public class ContinentsConsumerService
        implements ConsumerSeekAware {

    private final ObjectMapper objectMapper;

    // STORE LAST MESSAGE
    private Map<String, Integer> continentsData =
            new HashMap<>();

    public ContinentsConsumerService(
            ObjectMapper objectMapper
    ) {

        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = "stickers-continents-topic",
            groupId = "demo-group"
    )
    public void consume(
            String message
    ) {

        try {

            Map<String, Integer> data =
                    objectMapper.readValue(
                            message,
                            new TypeReference<Map<String, Integer>>() {}
                    );

            continentsData = data;

            System.out.println(
                    "Continents consumed: "
                            + continentsData
            );

        } catch (Exception e) {

            System.out.println(
                    "Error consuming continents topic"
            );

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

                    // GO TO LAST MESSAGE
                    callback.seekRelative(
                            topicPartition.topic(),
                            topicPartition.partition(),
                            -1,
                            true
                    );
                }
        );
    }

    // GET ALL
    public Map<String, Integer> getContinentsData() {

        return continentsData;
    }

    // GET SINGLE CONTINENT
    public Integer getContinentCount(
            String continent
    ) {

        return continentsData.get(
                continent.toUpperCase()
        );
    }
}