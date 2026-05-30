package com.wcpanini.demo.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "relations",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_one", "user_two"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Relation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_one", nullable = false)
    private String userOne;

    @Column(name = "user_two", nullable = false)
    private String userTwo;
}