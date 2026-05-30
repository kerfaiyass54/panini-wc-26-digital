package com.wcpanini.demo.entities;

import com.wcpanini.demo.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "invitations",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"sender", "receiver"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private String receiver;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
}