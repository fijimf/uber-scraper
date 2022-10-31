package com.fijimf.uberscraper.service.espn;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fijimf.uberscraper.model.espn.Scoreboard;
import com.fijimf.uberscraper.model.espn.ScoreboardGame;
import com.fijimf.uberscraper.model.espn.ScoreboardOdds;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonScraperTest {
    @Test
    void extractGameJson() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/event.json")) {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            ScoreboardGame scoreboardGame = objectMapper.readValue(inputStream, ScoreboardGame.class);
            assertThat(scoreboardGame.getId()).isEqualTo("401258956");
        }
    }

    @Test
    void extractOddsJson() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/odds.json")) {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            ScoreboardOdds odds = objectMapper.readValue(inputStream, ScoreboardOdds.class);
            assertThat(odds.getProvider().getPriority()).isEqualTo("1");
        }
    }

    @Test
    void extractScoreboardJson() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/scoreboard.json")) {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            Scoreboard scoreboard = objectMapper.readValue(inputStream, Scoreboard.class);
            assertThat(scoreboard.getSports()).isNotNull();
            System.err.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(scoreboard));
        }
    }
}
