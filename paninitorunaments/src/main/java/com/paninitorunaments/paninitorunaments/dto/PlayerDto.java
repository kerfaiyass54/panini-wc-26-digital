package com.paninitorunaments.paninitorunaments.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerDto {

    private Long id;

    private String name;

    private Integer ability;

    private String position;
}