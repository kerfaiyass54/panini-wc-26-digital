package com.paninitorunaments.paninitorunaments.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerInfo {

    private Long id;

    private String name;

    private String position;

    private Integer ability;

    private String nationality;
}