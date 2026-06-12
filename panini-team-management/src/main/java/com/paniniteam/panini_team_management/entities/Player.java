package com.paniniteam.panini_team_management.entities;

import com.paniniteam.panini_team_management.enums.Position;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "players")
public class Player extends BaseEntity {

    @NotBlank
    @Indexed(unique = true)
    private String name;

    @NotBlank
    @Indexed(unique = true)
    private String code;

    private Position position;

    @Min(1)
    @Max(100)
    private Integer ability;
}