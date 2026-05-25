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

    // STORE DATA BY EMAIL
    private Map<String, Map<String, Integer>> continentsData =
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

            Map<String, Object> payload =
                    objectMapper.readValue(
                            message,
                            new TypeReference<Map<String, Object>>() {}
                    );

            String email =
                    payload.get("email").toString();

            Map<String, Integer> data =
                    objectMapper.convertValue(
                            payload.get("data"),
                            new TypeReference<Map<String, Integer>>() {}
                    );

            continentsData.put(email, data);

            System.out.println(
                    "Continents consumed for "
                            + email
                            + ": "
                            + data
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

    // GET ALL DATA FOR EMAIL
    public Map<String, Integer> getContinentsData(
            String email
    ) {

        return continentsData.getOrDefault(
                email,
                new HashMap<>()
        );
    }

    // GET SINGLE CONTINENT FOR EMAIL
    public Integer getContinentCount(
            String email,
            String continent
    ) {

        return continentsData
                .getOrDefault(email, new HashMap<>())
                .get(continent.toUpperCase());
    }
}