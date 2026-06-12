package com.paniniteam.panini_team_management.entities;

import com.paniniteam.panini_team_management.enums.Tactic;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "teams")
public class Team extends BaseEntity {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @Builder.Default
    private List<String> playerIds = new ArrayList<>();

    private Tactic tactic;
}