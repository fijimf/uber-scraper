package com.fijimf.uberscraper.service.espn;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fijimf.uberscraper.model.espn.Scoreboard;
import com.fijimf.uberscraper.model.espn.ScoreboardGame;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

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
    void extractScoreboardJson() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/scoreboard.json")) {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            Scoreboard scoreboard = objectMapper.readValue(inputStream, Scoreboard.class);
            assertThat(scoreboard.getSports()).isNotNull();
        }
    }
}
