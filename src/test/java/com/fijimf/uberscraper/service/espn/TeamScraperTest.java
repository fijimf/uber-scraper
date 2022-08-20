package com.fijimf.uberscraper.service.espn;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import static com.fijimf.uberscraper.service.espn.Utils.ESPN_BASE;
import static org.assertj.core.api.Assertions.assertThat;

public class TeamScraperTest {
    @Test
    void teamDetailUrl() {
        String scoreboardUrl = TeamScraper.teamDetailUrl(123, "georgetown-hoyas");
        assertThat(scoreboardUrl).isEqualTo(ESPN_BASE + "/mens-college-basketball/team/_/id/123/georgetown-hoyas");
    }



    @Test
    void extractTeamConferenceMapping() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/conferences.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), ConferenceScraper.allConferenceStandingsUrl(2016));
            Map<String, String> teamUrls = ConferenceScraper.extractTeamConferenceMapping(document);
            assertThat(teamUrls).isNotNull();
            assertThat(teamUrls).isNotEmpty();
            assertThat(teamUrls).hasSize(351);
            assertThat(teamUrls.keySet()).allMatch(u -> TeamScraper.teamUrlPattern.matcher(u).matches());
        }
    }

    @Test
    void extractTeamUrls() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/teams.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), TeamScraper.ESPN_MCB_TEAMS);
            List<String> teamUrls = TeamScraper.extractTeamUrls(document);
            assertThat(teamUrls).isNotNull();
            assertThat(teamUrls).isNotEmpty();
            assertThat(teamUrls).hasSize(363);
            assertThat(teamUrls).allMatch(u -> TeamScraper.teamUrlPattern.matcher(u).matches());
        }
    }

}
