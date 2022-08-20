package com.fijimf.uberscraper.service.espn;

import org.jsoup.nodes.Document;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.fijimf.uberscraper.service.espn.Utils.ESPN_BASE;

public class TeamScraper {
    public static final Pattern teamUrlPattern = Pattern.compile("/mens-college-basketball/team/_/id/\\d{1,6}/[a-z\\d\\-]{3,48}");
    public static final String ESPN_MCB_TEAMS = ESPN_BASE + "/mens-college-basketball/teams";

    public static String teamDetailUrl(int teamId, String key) {
        return ESPN_BASE + "/mens-college-basketball/team/_/id/" + teamId + "/" + key;
    }

    public static List<String> extractTeamUrls(Document page) {
        return page.select("section.TeamLinks>a.AnchorLink").stream().map(e -> e.attr("href")).collect(Collectors.toList());
    }
}
