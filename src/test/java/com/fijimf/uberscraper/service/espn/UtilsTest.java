package com.fijimf.uberscraper.service.espn;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.fijimf.uberscraper.service.espn.Utils.ESPN_BASE;
import static org.assertj.core.api.Assertions.*;

class UtilsTest {

    @Test
    void seasonToDates() {
        List<LocalDate> dates = Utils.seasonToDates(2016);
        assertThat(dates).first().matches(d -> d.isEqual(LocalDate.of(2016, 11, 1)));
        assertThat(dates).last().matches(d -> d.isEqual(LocalDate.of(2017, 4, 29)));
        assertThat(dates).allMatch(
                d -> d.isAfter(LocalDate.of(2016, 10, 31)) &&
                        d.isBefore(LocalDate.of(2017, 5, 1)));
    }

    @Test
    void testYyyymmdd() {
        List<LocalDate> dates = Utils.seasonToDates(2016);
        assertThat(dates).allMatch(d -> d.equals(Utils.yyyymmdd(Utils.yyyymmdd(d))));
    }

    @Test
    void scoreboardUrl() {
        String scoreboardUrl = Utils.scoreboardUrl(LocalDate.of(2021, 3, 22));
        assertThat(scoreboardUrl).isEqualTo(ESPN_BASE + "/mens-college-basketball/scoreboard/_/group/50/date/20210322");
    }

    @Test
    void teamDetailUrl() {
        String scoreboardUrl = Utils.teamDetailUrl(123, "georgetown-hoyas");
        assertThat(scoreboardUrl).isEqualTo(ESPN_BASE + "/mens-college-basketball/team/_/id/123/georgetown-hoyas");
    }

    @Test
    void gameUrl() {
        String gameUrl = Utils.gameUrl(123456789);
        assertThat(gameUrl).isEqualTo(ESPN_BASE + "/mens-college-basketball/game/_/gameId/123456789");
    }

    @Test
    void extractGameIdsFromScoreboard() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/scoreboard.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            List<String> details = Utils.extractGameIdsFromScoreboard(document);
            assertThat(details).isNotNull();
            assertThat(details).isNotEmpty();
            assertThat(details).hasSize(13);
        }
    }

    @Test
    void extractTeamConferenceMapping() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/conferences.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            Map<String, String> teamUrls = Utils.extractTeamConferenceMapping(document);
            assertThat(teamUrls).isNotNull();
            assertThat(teamUrls).isNotEmpty();
            assertThat(teamUrls).hasSize(351);
            assertThat(teamUrls.keySet()).allMatch(u -> Utils.teamUrlPattern.matcher(u).matches());
        }
    }

    @Test
    void extractTeamUrls() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/teams.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.ESPN_MCB_TEAMS);
            List<String> teamUrls = Utils.extractTeamUrls(document);
            assertThat(teamUrls).isNotNull();
            assertThat(teamUrls).isNotEmpty();
            assertThat(teamUrls).hasSize(363);
            assertThat(teamUrls).allMatch(u -> Utils.teamUrlPattern.matcher(u).matches());
        }
    }

    @Test
    void extractConferenceDetails() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/conferences.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            List<RawConferenceDetails> details = Utils.extractConferenceDetails(document);
            assertThat(details).isNotNull();
            assertThat(details).isNotEmpty();
            assertThat(details).hasSize(32);
        }
    }

    @Test
    void conferenceConsistency() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/conferences.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            Set<String> confs = Utils.extractConferenceDetails(document).stream().map(RawConferenceDetails::getName).collect(Collectors.toSet());
            Map<String, String> teamUrls = Utils.extractTeamConferenceMapping(document);

            assertThat(teamUrls.values()).allMatch(confs::contains);
        }
    }


    @Test
    void allConferenceStandingsUrl() {
        String url = Utils.allConferenceStandingsUrl(2022);
        assertThat(url).isEqualTo(ESPN_BASE + "/mens-college-basketball/standings/_/season/2022");
    }

    @Test
    void extractGameDetails() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-2.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            RawGameDetails details = Utils.extractGameDetails(document);
            assertThat(details).isNotNull();
        }
    }

    @Test
    void extractHeaderGameDetails() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-1.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            String details = Utils.extractHeaderGameDetails(document);
            assertThat(details).isEqualTo("");
        }
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-2.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            String details = Utils.extractHeaderGameDetails(document);
            assertThat(details).isEqualTo("Naples Invitational");
        }
    }

    @Test
    void extractHeaderStatusDetails() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-1.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            String details = Utils.extractHeaderStatusDetails(document);
            assertThat(details).isEqualTo("Final");
        }
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-2.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            String details = Utils.extractHeaderStatusDetails(document);
            assertThat(details).isEqualTo("Final");
        }
    }

    @Test
    void extractHomeAwayDetails() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-1.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            Map<String, String> detailsH = Utils.extractHomeAwayDetails(document.select("header.game-package div.competitors div.home").first());
            assertThat(detailsH).hasSize(6);
            Map<String, String> detailsA = Utils.extractHomeAwayDetails(document.select("header.game-package div.competitors div.away").first());
            assertThat(detailsA).hasSize(6);

        }
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-2.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            Map<String, String> detailsH = Utils.extractHomeAwayDetails(document.select("header.game-package div.competitors div.home").first());
            assertThat(detailsH).hasSize(6);
            Map<String, String> detailsA = Utils.extractHomeAwayDetails(document.select("header.game-package div.competitors div.away").first());
            assertThat(detailsA).hasSize(6);
        }
    }

    @Test
    void gameDetailNumberOfPeriods() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-1.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            int numberOfPeriods = Utils.gameDetailNumberOfPeriods(document);
            assertThat(numberOfPeriods).isEqualTo(2);
        }
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-2.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            int numberOfPeriods = Utils.gameDetailNumberOfPeriods(document);
            assertThat(numberOfPeriods).isEqualTo(2);
        }
    }

    @Test
    void gameDetailLocation() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-1.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            String location = Utils.gameDetailLocation(document);
            assertThat(location).isEqualTo("Holmes Convocation Center");
        }
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-2.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            String location = Utils.gameDetailLocation(document);
            assertThat(location).isEqualTo("Moe Kent Family Field House");
        }
    }

    @Test
    void gameDetailTime() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-1.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            String location = Utils.gameDetailTime(document);
            assertThat(location).isEqualTo("6:30 PM ET");
        }
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-2.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            String location = Utils.gameDetailTime(document);
            assertThat(location).isEqualTo("8:00 PM ET");
        }
    }

    @Test
    void gameDetailDate() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-1.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            String location = Utils.gameDetailDate(document);
            assertThat(location).isEqualTo("November 29, 2021");


        }
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-2.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            String location = Utils.gameDetailDate(document);
            assertThat(location).isEqualTo("November 24, 2021");
        }
    }

    @Test
    void gameDetailFavorite() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-1.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            String favorite = Utils.favorite(document);
            assertThat(favorite).isEqualTo("APP");
        }
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-2.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            String favorite = Utils.favorite(document);
            assertThat(favorite).isEqualTo("KENT");
        }
    }

    @Test
    void gameDetailLine() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-1.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            Double line = Utils.line(document);
            assertThat(line).isEqualTo(-8.5);
        }
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-2.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            Double line = Utils.line(document);
            assertThat(line).isEqualTo(-2.5);
        }
    }

    @Test
    void gameDetailOverUnder() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-1.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            Double overUnder = Utils.overUnder(document);
            assertThat(overUnder).isEqualTo(130.5);
        }
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/gameDetail-2.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), Utils.allConferenceStandingsUrl(2016));
            Double overUnder = Utils.overUnder(document);
            assertThat(overUnder).isEqualTo(134.0);
        }
    }
}