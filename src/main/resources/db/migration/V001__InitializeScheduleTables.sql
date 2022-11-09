CREATE TABLE espn_season_scrape
(
    id           BIGSERIAL PRIMARY KEY,
    season       INT         NOT NULL,
    from_date    DATE        NULL,
    to_date      DATE        NULL,
    started_at   TIMESTAMP   NOT NULL,
    completed_at TIMESTAMP   NULL,
    status       VARCHaR(32) NOT NULL
);

CREATE TABLE espn_scoreboard_scrape
(
    id                    BIGSERIAL PRIMARY KEY,
    espn_season_scrape_id BIGINT       NOT NULL REFERENCES espn_season_scrape (id),
    scoreboard_key        DATE         NOT NULL,
    flavor                VARCHAR(64)  NOT NULL,
    url                   VARCHAR(192) NOT NULL,
    retrieved_at          TIMESTAMP    NULL,
    response_time_ms      BIGINT       NULL,
    response_code         INT          NULL,
    response              TEXT         NULL,
    number_of_games       INT          NULL
);
