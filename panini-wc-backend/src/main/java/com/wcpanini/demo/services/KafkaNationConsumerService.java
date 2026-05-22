package com.wcpanini.demo.services;

import org.apache.kafka.common.TopicPartition;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class KafkaNationConsumerService
        implements ConsumerSeekAware {

    private final ObjectMapper objectMapper;

    // STORE LAST RECEIVED DATA
    private final Map<String, Integer> stickersStats =
            new ConcurrentHashMap<>();

    public KafkaNationConsumerService(
            ObjectMapper objectMapper
    ) {

        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = "stickers-nations-topic",
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

            stickersStats.clear();

            stickersStats.putAll(data);

            System.out.println(
                    "NEW DATA RECEIVED: " + stickersStats
            );

        } catch (Exception e) {

            System.out.println(
                    "ERROR CONSUMING MESSAGE"
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

                    callback.seekRelative(
                            topicPartition.topic(),
                            topicPartition.partition(),
                            -1,
                            true
                    );
                }
        );
    }

    // RETURN ALL DATA
    public Map<String, Integer> getAllStats() {

        return stickersStats;
    }

    // RETURN ONE COUNTRY
    public Integer getCountryCount(
            String country
    ) {

        return stickersStats.get(
                country.toLowerCase()
        );
    }
}