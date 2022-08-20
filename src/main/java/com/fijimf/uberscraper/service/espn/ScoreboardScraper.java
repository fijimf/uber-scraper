package com.fijimf.uberscraper.service.espn;

import org.jsoup.nodes.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.fijimf.uberscraper.service.espn.Utils.ESPN_BASE;
import static com.fijimf.uberscraper.service.espn.Utils.yyyymmdd;

public class ScoreboardScraper {

    public static String scoreboardUrl(LocalDate d) {
        return ESPN_BASE + "/mens-college-basketball/scoreboard/_/group/50/date/" + yyyymmdd(d);
    }
    public static List<String> extractGameIdsFromScoreboard(Document page) {
        return page
                .select("section.Scoreboard")
                .stream()
                .map(e -> e.attr("id"))
                .collect(Collectors.toList());
    }

}
