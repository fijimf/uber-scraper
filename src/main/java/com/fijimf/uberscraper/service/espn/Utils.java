package com.fijimf.uberscraper.service.espn;

import org.jsoup.nodes.Document;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {
    public static final String ESPN_BASE = "https://www.espn.com";

    public static List<LocalDate> seasonToDates(int year) {
        List<LocalDate> l = new ArrayList<>();
        for (LocalDate d = LocalDate.of(year, 11, 1);
             d.isBefore(LocalDate.of(year + 1, 4, 30));
             d = d.plusDays(1))
            l.add(d);
        return l;
    }

    public static String yyyymmdd(LocalDate d) {
        return d.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
   public static LocalDate yyyymmdd(String s) {
        return LocalDate.parse(s,DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static String allConferenceStandingsUrl(int season) {
        return ESPN_BASE + "/mens-college-basketball/standings/_/season/" + season;
    }

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

    public static Map<String, String> extractTeamConferenceMapping(Document page) {
        Map<String, String> map = new HashMap<>();
        page.select("div.ResponsiveTable").forEach(e -> {
            String z = e.select("div.Table__Title").text();
            e.select("div.team-link span.hide-mobile a").forEach(f -> {
                map.put(f.attr("href"), z);
            });
        });
        return map;
    }
}
