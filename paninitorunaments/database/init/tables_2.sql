CREATE TABLE standing(
                         id BIGSERIAL PRIMARY KEY,

                         championnat_id BIGINT NOT NULL,
                         team_id BIGINT NOT NULL,

                         played INTEGER DEFAULT 0,
                         won INTEGER DEFAULT 0,
                         drawn INTEGER DEFAULT 0,
                         lost INTEGER DEFAULT 0,

                         goals_for INTEGER DEFAULT 0,
                         goals_against INTEGER DEFAULT 0,
                         goal_difference INTEGER DEFAULT 0,

                         points INTEGER DEFAULT 0,

                         CONSTRAINT fk_standing_championnat
                             FOREIGN KEY(championnat_id)
                                 REFERENCES championnat(id),

                         CONSTRAINT fk_standing_team
                             FOREIGN KEY(team_id)
                                 REFERENCES team(id)
);