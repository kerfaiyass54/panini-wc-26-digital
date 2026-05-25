package com.wcpanini.demo.repositories;

import com.wcpanini.demo.entities.Owning;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OwningRepository extends JpaRepository<Owning, Long> {

    boolean existsByEmailAndCode(String email, String code);

    Optional<Owning> findByEmailAndCode(String email, String code);

    List<Owning> findAllByEmail(String email);
}