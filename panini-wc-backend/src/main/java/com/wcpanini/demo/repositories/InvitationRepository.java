package com.wcpanini.demo.repositories;

import com.wcpanini.demo.entities.Invitation;
import com.wcpanini.demo.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvitationRepository
        extends JpaRepository<Invitation, Long> {

    Optional<Invitation> findBySenderAndReceiver(
            String sender,
            String receiver
    );

    List<Invitation> findByReceiver(String receiver);

    List<Invitation> findByStatus(Status status);
}