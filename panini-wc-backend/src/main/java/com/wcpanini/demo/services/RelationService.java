package com.wcpanini.demo.services;

import com.wcpanini.demo.entities.Invitation;
import com.wcpanini.demo.entities.Owning;
import com.wcpanini.demo.entities.Relation;
import com.wcpanini.demo.enums.Status;
import com.wcpanini.demo.repositories.InvitationRepository;
import com.wcpanini.demo.repositories.OwningRepository;
import com.wcpanini.demo.repositories.RelationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class RelationService {

    private final InvitationRepository invitationRepository;
    private final RelationRepository relationRepository;
    private final OwningRepository owningRepository;


    public void sendInvite(
            String sender,
            String receiver
    ) {

        if (sender.equals(receiver)) {
            throw new RuntimeException(
                    "Cannot invite yourself"
            );
        }

        Optional<Invitation> existing =
                invitationRepository
                        .findBySenderAndReceiver(
                                sender,
                                receiver
                        );

        if (existing.isPresent()
                && existing.get().getStatus() == Status.PENDING) {

            throw new RuntimeException(
                    "Invitation already pending"
            );
        }

        boolean alreadyConnected =
                relationRepository.existsByUserOneAndUserTwo(
                        sender,
                        receiver
                )
                        ||
                        relationRepository.existsByUserTwoAndUserOne(
                                sender,
                                receiver
                        );

        if (alreadyConnected) {
            throw new RuntimeException(
                    "Users already connected"
            );
        }

        Invitation invitation =
                Invitation.builder()
                        .sender(sender)
                        .receiver(receiver)
                        .status(Status.PENDING)
                        .build();

        invitationRepository.save(invitation);
    }


    public void changeStatus(
            Long invitationId,
            String status
    ) {

        Invitation invitation =
                invitationRepository.findById(invitationId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Invitation not found"
                                )
                        );

        Status newStatus =
                Status.valueOf(status.toUpperCase());

        invitation.setStatus(newStatus);

        if (newStatus == Status.ACCEPTED) {

            Relation relation =
                    Relation.builder()
                            .userOne(invitation.getSender())
                            .userTwo(invitation.getReceiver())
                            .build();

            relationRepository.save(relation);
        }

        invitationRepository.save(invitation);
    }


    public List<String> getRelations(
            String username
    ) {

        List<Relation> relations =
                relationRepository
                        .findByUserOneOrUserTwo(
                                username,
                                username
                        );

        List<String> result = new ArrayList<>();

        for (Relation relation : relations) {

            if (relation.getUserOne()
                    .equals(username)) {

                result.add(
                        relation.getUserTwo()
                );

            } else {

                result.add(
                        relation.getUserOne()
                );
            }
        }

        return result;
    }


    public List<String> getNotConnectedUsers(
            String email
    ) {

        return getUsers()
                .stream()
                .filter(user ->
                        !user.equals(email)
                )
                .filter(user ->

                        !relationRepository
                                .existsByUserOneAndUserTwo(
                                        email,
                                        user
                                )

                                &&

                                !relationRepository
                                        .existsByUserTwoAndUserOne(
                                                email,
                                                user
                                        )
                )
                .distinct()
                .toList();
    }

    public List<String> getUsers(){
        return owningRepository.findAll().stream().map(Owning::getEmail).toList();
    }

    public List<Invitation> getPendingInvitations(String receiver) {

        return invitationRepository.findByReceiverAndStatus(
                receiver,
                Status.PENDING
        );
    }

    public List<Invitation> getSentPendingInvitations(String sender) {

        return invitationRepository.findBySenderAndStatus(
                sender,
                Status.PENDING
        );
    }
}