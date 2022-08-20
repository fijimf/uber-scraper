package com.fijimf.uberscraper.service.espn;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static com.fijimf.uberscraper.service.espn.Utils.ESPN_BASE;
import static org.assertj.core.api.Assertions.assertThat;

public class GameScraperTest {

    @Test
    void extractHeaderStatusDetails() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-1.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), GameScraper.gameUrl(1234567));
            String details = GameScraper.statusDetails(document);
            assertThat(details).isEqualTo("Final");
        }
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-2.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), GameScraper.gameUrl(1234567));
            String details = GameScraper.statusDetails(document);
            assertThat(details).isEqualTo("Final");
        }
    }

    @Test
    void extractGameDetails() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-2.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), GameScraper.gameUrl(1234567));
            Optional<RawGameDetails> details = GameScraper.extractGameDetails(document);
            assertThat(details).isPresent();
        }
    }

    @Test
    void extractHeaderGameDetails() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-1.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), GameScraper.gameUrl(1234567));
            String details = GameScraper.gameDetails(document);
            assertThat(details).isEqualTo("");
        }
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-2.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), GameScraper.gameUrl(1234567));
            String details = GameScraper.gameDetails(document);
            assertThat(details).isEqualTo("Naples Invitational");
        }
    }

    @Test
    void extractHomeAwayDetails() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-1.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), GameScraper.gameUrl(1234567));
            Map<String, String> detailsH = GameScraper.homeAwayDetails(document.select("header.game-package div.competitors div.home").first());
            assertThat(detailsH).hasSize(6);
            Map<String, String> detailsA = GameScraper.homeAwayDetails(document.select("header.game-package div.competitors div.away").first());
            assertThat(detailsA).hasSize(6);

        }
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-2.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), GameScraper.gameUrl(1234567));
            Map<String, String> detailsH = GameScraper.homeAwayDetails(document.select("header.game-package div.competitors div.home").first());
            assertThat(detailsH).hasSize(6);
            Map<String, String> detailsA = GameScraper.homeAwayDetails(document.select("header.game-package div.competitors div.away").first());
            assertThat(detailsA).hasSize(6);
        }
    }

    @Test
    void gameDetailNumberOfPeriods() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-1.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), GameScraper.gameUrl(1234567));
            int numberOfPeriods = GameScraper.numOfPeriods(document);
            assertThat(numberOfPeriods).isEqualTo(2);
        }
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-2.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), GameScraper.gameUrl(1234567));
            int numberOfPeriods = GameScraper.numOfPeriods(document);
            assertThat(numberOfPeriods).isEqualTo(2);
        }
    }

    @Test
    void gameDetailLocation() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-1.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), GameScraper.gameUrl(1234567));
            String location = GameScraper.location(document);
            assertThat(location).isEqualTo("Holmes Convocation Center");
        }
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-2.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), GameScraper.gameUrl(1234567));
            String location = GameScraper.location(document);
            assertThat(location).isEqualTo("Moe Kent Family Field House");
        }
    }

    @Test
    void gameDetailTime() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-1.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), GameScraper.gameUrl(1234567));
            LocalDateTime time = GameScraper.time(document);
            assertThat(time).isEqualTo(LocalDateTime.of(2021,11,29,18,30));
        }
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-2.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), GameScraper.gameUrl(1234567));
            LocalDateTime time = GameScraper.time(document);
            assertThat(time).isEqualTo(LocalDateTime.of(2021,11,24,20,0));
        }
    }

    @Test
    void gameDetailDate() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-1.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), GameScraper.gameUrl(1234567));
            LocalDate date = GameScraper.date(document);
            assertThat(date).isEqualTo(LocalDate.of(2021,11,29));


        }
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-2.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), GameScraper.gameUrl(1234567));
            LocalDate date =  GameScraper.date(document);
            assertThat(date).isEqualTo(LocalDate.of(2021,11,24));
        }
    }

    @Test
    void gameDetailFavorite() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-1.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), GameScraper.gameUrl(1234567));
            String favorite = GameScraper.favorite(document);
            assertThat(favorite).isEqualTo("APP");
        }
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-2.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), GameScraper.gameUrl(1234567));
            String favorite = GameScraper.favorite(document);
            assertThat(favorite).isEqualTo("KENT");
        }
    }

    @Test
    void gameDetailLine() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-1.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), GameScraper.gameUrl(1234567));
            Double line = GameScraper.line(document);
            assertThat(line).isEqualTo(-8.5);
        }
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-2.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), GameScraper.gameUrl(1234567));
            Double line = GameScraper.line(document);
            assertThat(line).isEqualTo(-2.5);
        }
    }

    @Test
    void gameDetailOverUnder() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-1.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), GameScraper.gameUrl(1234567));
            Double overUnder = GameScraper.overUnder(document);
            assertThat(overUnder).isEqualTo(130.5);
        }
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-2.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), GameScraper.gameUrl(1234567));
            Double overUnder = GameScraper.overUnder(document);
            assertThat(overUnder).isEqualTo(134.0);
        }
    }

    @Test
    void gameUrl() {
        String gameUrl = GameScraper.gameUrl(123456789);
        assertThat(gameUrl).isEqualTo(ESPN_BASE + "/mens-college-basketball/game/_/gameId/123456789");
    }

}
