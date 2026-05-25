package com.wcpanini.demo.services;

import com.wcpanini.demo.dtos.StatisticsResponse;
import com.wcpanini.demo.repositories.OwningRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class StatisticsService {

    private final OwningRepository owningRepository;

    public StatisticsService(
            OwningRepository owningRepository
    ) {
        this.owningRepository = owningRepository;
    }

    public StatisticsResponse getStatistics(
            String email
    ) {

        long totalOwnedStickers =
                owningRepository.countByEmail(email);

        long totalFinishedCountries =
                owningRepository.countFinishedCountries(email);

        long totalLogos =
                owningRepository.countOwnedLogos(email);

        long totalPlayers =
                owningRepository.countOwnedPlayers(email);

        return new StatisticsResponse(
                totalOwnedStickers,
                totalFinishedCountries,
                totalLogos,
                totalPlayers
        );
    }
}