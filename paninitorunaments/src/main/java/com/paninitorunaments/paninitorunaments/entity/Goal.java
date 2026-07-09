package com.paninitorunaments.paninitorunaments.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer minute;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
}