package com.fijimf.uberscraper.service.espn;

import com.fijimf.uberscraper.model.espn.ScoreboardGame;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.fijimf.uberscraper.service.espn.Utils.ESPN_BASE;
import static org.assertj.core.api.Assertions.assertThat;

public class GameScraperTest {

    @Test
    void extractGameDetails() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/scoreboard.json")) {
            List<ScoreboardGame> details = GameScraper.extractGameDetails(new String(inputStream.readAllBytes()));
            assertThat(details).isNotEmpty();
        }
    }
}
