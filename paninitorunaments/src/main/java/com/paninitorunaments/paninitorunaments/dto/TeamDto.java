package com.paninitorunaments.paninitorunaments.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamDto {

    private String name;

    private List<PlayerDto> players;
}