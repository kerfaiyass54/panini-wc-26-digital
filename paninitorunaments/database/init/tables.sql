-- =========================================
-- PLAYER
-- =========================================
CREATE TABLE player (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        ability INTEGER NOT NULL,
                        nationality VARCHAR(100),
                        position VARCHAR(50)
);

-- =========================================
-- TEAM
-- =========================================
CREATE TABLE team (
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(255) NOT NULL UNIQUE,
                      date_create TIMESTAMP NOT NULL
);

-- =========================================
-- TEAM PLAYERS
-- =========================================
ALTER TABLE player
    ADD COLUMN team_id BIGINT;

ALTER TABLE player
    ADD CONSTRAINT fk_player_team
        FOREIGN KEY (team_id)
            REFERENCES team(id)
            ON DELETE CASCADE;

-- =========================================
-- GOAL
-- =========================================
CREATE TABLE goal (
                      id BIGSERIAL PRIMARY KEY,
                      minute INTEGER NOT NULL,
                      player_id BIGINT NOT NULL,

                      CONSTRAINT fk_goal_player
                          FOREIGN KEY (player_id)
                              REFERENCES player(id)
                              ON DELETE CASCADE
);

-- =========================================
-- MATCH
-- =========================================
CREATE TABLE football_match (
                                id BIGSERIAL PRIMARY KEY,

                                team1_id BIGINT NOT NULL,
                                team2_id BIGINT NOT NULL,

                                goals_home INTEGER DEFAULT 0,
                                goals_away INTEGER DEFAULT 0,

                                CONSTRAINT fk_match_team1
                                    FOREIGN KEY (team1_id)
                                        REFERENCES team(id),

                                CONSTRAINT fk_match_team2
                                    FOREIGN KEY (team2_id)
                                        REFERENCES team(id),

                                CONSTRAINT chk_different_teams
                                    CHECK (team1_id <> team2_id)
);

-- =========================================
-- MATCH GOALS
-- =========================================
ALTER TABLE goal
    ADD COLUMN match_id BIGINT;

ALTER TABLE goal
    ADD CONSTRAINT fk_goal_match
        FOREIGN KEY (match_id)
            REFERENCES football_match(id)
            ON DELETE CASCADE;

-- =========================================
-- CHAMPIONNAT
-- =========================================
CREATE TABLE championnat (
                             id BIGSERIAL PRIMARY KEY,
                             tournament VARCHAR(255) NOT NULL
);

-- =========================================
-- CHAMPIONNAT TEAMS
-- =========================================
CREATE TABLE championnat_teams (
                                   championnat_id BIGINT NOT NULL,
                                   team_id BIGINT NOT NULL,

                                   PRIMARY KEY (championnat_id, team_id),

                                   CONSTRAINT fk_ct_championnat
                                       FOREIGN KEY (championnat_id)
                                           REFERENCES championnat(id)
                                           ON DELETE CASCADE,

                                   CONSTRAINT fk_ct_team
                                       FOREIGN KEY (team_id)
                                           REFERENCES team(id)
                                           ON DELETE CASCADE
);

-- =========================================
-- CHAMPIONNAT MATCHES
-- =========================================
CREATE TABLE championnat_matches (
                                     championnat_id BIGINT NOT NULL,
                                     match_id BIGINT NOT NULL,

                                     PRIMARY KEY (championnat_id, match_id),

                                     CONSTRAINT fk_cm_championnat
                                         FOREIGN KEY (championnat_id)
                                             REFERENCES championnat(id)
                                             ON DELETE CASCADE,

                                     CONSTRAINT fk_cm_match
                                         FOREIGN KEY (match_id)
                                             REFERENCES football_match(id)
                                             ON DELETE CASCADE
);