package com.wcpanini.demo.services;


import com.wcpanini.demo.dtos.DuplicateRequest;
import com.wcpanini.demo.entities.Duplicate;
import com.wcpanini.demo.repositories.DuplicateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DuplicateService {

    private final DuplicateRepository duplicateRepository;

    @Transactional(readOnly = true)
    public List<Duplicate> getDuplicates(
            String email
    ) {

        return duplicateRepository
                .findAllByEmailOrderByCreatedAtDesc(
                        email
                );
    }

    public void reduceDuplicate(
            DuplicateRequest request
    ) {

        Duplicate duplicate =
                duplicateRepository
                        .findByEmailAndCode(
                                request.getEmail(),
                                request.getPlace()
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Duplicate not found"
                                )
                        );

        if (duplicate.getNumber() <= 2) {

            duplicateRepository.delete(
                    duplicate
            );

            return;
        }

        duplicate.setNumber(
                duplicate.getNumber() - 1
        );

        duplicateRepository.save(
                duplicate
        );
    }

    public void deleteDuplicate(
            DuplicateRequest request
    ) {

        Duplicate duplicate =
                duplicateRepository
                        .findByEmailAndCode(
                                request.getEmail(),
                                request.getPlace()
                        )
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Duplicate not found"
                                )
                        );

        duplicateRepository.delete(
                duplicate
        );
    }



}