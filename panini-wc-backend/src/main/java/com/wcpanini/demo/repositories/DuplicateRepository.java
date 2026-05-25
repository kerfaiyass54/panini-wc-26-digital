// DuplicateRepository.java
package com.wcpanini.demo.repositories;

import com.wcpanini.demo.entities.Duplicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DuplicateRepository extends JpaRepository<Duplicate, Long> {

    Optional<Duplicate> findByEmailAndCode(String email, String code);

    Page<Duplicate> findAllByEmail(String email, Pageable pageable);

    void deleteByEmailAndCode(String email, String code);
}