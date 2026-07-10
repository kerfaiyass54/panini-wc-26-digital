package com.paninitorunaments.paninitorunaments.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddTeamsRequest {

    private List<Long> teamIds;
}