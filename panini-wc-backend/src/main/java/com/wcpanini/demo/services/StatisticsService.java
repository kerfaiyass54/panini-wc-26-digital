package com.wcpanini.demo.services;

import com.wcpanini.demo.dtos.StatisticsResponse;
import com.wcpanini.demo.repositories.OwningRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
                Optional.ofNullable(
                        owningRepository.countByEmail(email)
                ).orElse(0L);

        long totalFinishedCountries =
                Optional.of(
                        owningRepository.finishedCountries(email).size()
                ).orElse(0);

        long totalLogos =
                Optional.ofNullable(
                        owningRepository.countOwnedLogos(email)
                ).orElse(0L);

        long totalPlayers =
                Optional.ofNullable(
                        owningRepository.countOwnedPlayers(email)
                ).orElse(0L);

        return new StatisticsResponse(
                totalOwnedStickers,
                totalFinishedCountries,
                totalLogos,
                totalPlayers
        );
    }

    public List<String> getFinishedCoutnries(String email){
        return owningRepository.finishedCountries(email);
    }
}