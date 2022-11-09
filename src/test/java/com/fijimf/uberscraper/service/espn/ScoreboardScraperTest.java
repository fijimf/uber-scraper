package com.fijimf.uberscraper.service.espn;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fijimf.uberscraper.model.espn.Scoreboard;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;

import static com.fijimf.uberscraper.service.espn.Utils.ESPN_BASE;
import static org.assertj.core.api.Assertions.assertThat;

public class ScoreboardScraperTest {
    @Test
    void scoreboardUrl() {
        String scoreboardUrl = ScoreboardScraper.scoreboardUrl(LocalDate.of(2021, 3, 22));
        assertThat(scoreboardUrl).isEqualTo(ESPN_BASE + "/mens-college-basketball/scoreboard/_/group/50/date/20210322");
    }

    @Test
    void extractGameIdsFromScoreboard() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/scoreboard.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), ConferenceScraper.allConferenceStandingsUrl(2016));
            List<String> details = ScoreboardScraper.extractGameIdsFromScoreboard(document);
            assertThat(details).isNotNull();
            assertThat(details).isNotEmpty();
            assertThat(details).hasSize(13);
        }
    }

    @Test
    void numberOfGames() throws IOException {

        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/scoreboard.json")) {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            Scoreboard scoreboard = objectMapper.readValue(inputStream, Scoreboard.class);
            assertThat(scoreboard.numberOfGames()).isEqualTo(3L);
        }

    }
}
