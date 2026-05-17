package com.wcpanini.demo.services;



import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Service
public class ContinentsConsumerService {

    private final ObjectMapper objectMapper;

    // STORE LAST MESSAGE

    private Map<String, Integer> continentsData =
            new HashMap<>();

    public ContinentsConsumerService(
            ObjectMapper objectMapper
    ) {

        this.objectMapper =
                objectMapper;
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