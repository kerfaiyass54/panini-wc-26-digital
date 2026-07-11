package com.wcpanini.demo.services;

import com.wcpanini.demo.dtos.OwnedPlayersRequest;
import com.wcpanini.demo.entities.Owning;
import com.wcpanini.demo.kafka.OwnedPlayersProducer;
import com.wcpanini.demo.repositories.OwningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectionService {

    private final OwningRepository owningRepository;

    private final OwnedPlayersProducer producer;

    public void generateTeam(String email) {

        List<String> playerNames =
                owningRepository
                        .findByEmail(email)
                        .stream()
                        .map(Owning::getCode)
                        .toList();

        producer.send(
                OwnedPlayersRequest.builder()
                        .email(email)
                        .playerNames(playerNames)
                        .build()
        );
    }
}