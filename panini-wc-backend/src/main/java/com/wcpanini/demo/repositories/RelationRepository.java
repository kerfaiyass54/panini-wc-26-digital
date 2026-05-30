package com.wcpanini.demo.repositories;

import com.wcpanini.demo.entities.Relation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RelationRepository
        extends JpaRepository<Relation, Long> {

    List<Relation> findByUserOneOrUserTwo(
            String userOne,
            String userTwo
    );

    boolean existsByUserOneAndUserTwo(
            String userOne,
            String userTwo
    );

    boolean existsByUserTwoAndUserOne(
            String userTwo,
            String userOne
    );
}