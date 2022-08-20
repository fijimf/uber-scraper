package com.fijimf.uberscraper.service.espn;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.fijimf.uberscraper.service.espn.Utils.ESPN_BASE;
import static org.assertj.core.api.Assertions.assertThat;

public class ConferenceScraperTest {
    @Test
    void extractConferenceDetails() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/conferences.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), ConferenceScraper.allConferenceStandingsUrl(2016));
            List<RawConferenceDetails> details = ConferenceScraper.extractConferenceDetails(document);
            assertThat(details).isNotNull();
            assertThat(details).isNotEmpty();
            assertThat(details).hasSize(32);
        }
    }

    @Test
    void conferenceConsistency() throws IOException {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("pages/conferences.html")) {
            Document document = Jsoup.parse(inputStream, Charset.defaultCharset().name(), ConferenceScraper.allConferenceStandingsUrl(2016));
            Set<String> confs = ConferenceScraper.extractConferenceDetails(document).stream().map(RawConferenceDetails::getName).collect(Collectors.toSet());
            Map<String, String> teamUrls = ConferenceScraper.extractTeamConferenceMapping(document);

            assertThat(teamUrls.values()).allMatch(confs::contains);
        }
    }

    @Test
    void allConferenceStandingsUrl() {
        String url = ConferenceScraper.allConferenceStandingsUrl(2022);
        assertThat(url).isEqualTo(ESPN_BASE + "/mens-college-basketball/standings/_/season/2022");
    }
}
