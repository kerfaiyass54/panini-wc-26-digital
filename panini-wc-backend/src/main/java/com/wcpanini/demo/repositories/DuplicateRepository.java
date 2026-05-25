// DuplicateRepository.java
package com.wcpanini.demo.repositories;

import com.wcpanini.demo.entities.Duplicate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DuplicateRepository extends JpaRepository<Duplicate, Long> {

    Optional<Duplicate> findByEmailAndCode(String email, String code);

    List<Duplicate> findAllByEmail(String email);

    void deleteByEmailAndCode(String email, String code);
}