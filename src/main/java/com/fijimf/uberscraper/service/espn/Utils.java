package com.fijimf.uberscraper.service.espn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Utils {
    public static final String ESPN_BASE = "https://www.espn.com";
    public static final String ESPN_MCB_TEAMS = ESPN_BASE + "/mens-college-basketball/teams";

    public static final Pattern teamUrlPattern = Pattern.compile("/mens-college-basketball/team/_/id/\\d{1,6}/[a-z\\d\\-]{3,48}");

    private static Pattern linePattern = Pattern.compile("Line: ([A-Z]+) (\\-\\d+\\.\\d)");
    private static Pattern overUnderPattern = Pattern.compile("Over/Under: (\\d+\\.\\d)");

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
        return LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static String allConferenceStandingsUrl(int season) {
        return ESPN_BASE + "/mens-college-basketball/standings/_/season/" + season;
    }

    public static String scoreboardUrl(LocalDate d) {
        return ESPN_BASE + "/mens-college-basketball/scoreboard/_/group/50/date/" + yyyymmdd(d);
    }

    public static String teamDetailUrl(int teamId, String key) {
        return ESPN_BASE + "/mens-college-basketball/team/_/id/" + teamId + "/" + key;
    }

    public static String gameUrl(int gameId) {
        return ESPN_BASE + "/mens-college-basketball/game/_/gameId/" + gameId;
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

    public static List<RawConferenceDetails> extractConferenceDetails(Document page) throws JsonProcessingException {
        Elements select = page.select("div#espnfitt+script");
        String json = select.dataNodes().get(0).getWholeData().replace("window['__espnfitt__']=", "");
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.readValue(json, Map.class);
        List<Map<String, Object>> extendedDetails = deepListExtract(map, Arrays.asList("page", "content", "headerscoreboard", "collegeConfs"));
        return extendedDetails.stream().map(RawConferenceDetails::fromExtendedDetailsMap).filter(c -> !c.getShortName().equals("Division I")).collect(Collectors.toList());
    }

    public static RawGameDetails extractGameDetails(Document page) {
        Map<String, String> homeData = Utils.extractHomeAwayDetails(page.select("header.game-package div.competitors div.home").first());
        Map<String, String> awayData = Utils.extractHomeAwayDetails(page.select("header.game-package div.competitors div.away").first());
        return new RawGameDetails(
                Utils.extractHeaderGameDetails(page),
                homeData.get("rank"),
                homeData.get("url"),
                homeData.get("shortName"),
                homeData.get("longName"),
                homeData.get("abbrev"),
                homeData.get("score"),
                awayData.get("rank"),
                awayData.get("url"),
                awayData.get("shortName"),
                awayData.get("longName"),
                awayData.get("abbrev"),
                awayData.get("score"),
                Utils.gameDetailNumberOfPeriods(page),
                Utils.extractHeaderStatusDetails(page),
                LocalDate.now(),//FIXME
                LocalDateTime.now(),//FIXME
                Utils.gameDetailLocation(page),
                Utils.favorite(page),
                Utils.line(page),
                Utils.overUnder(page)
        );
    }

    public static String extractHeaderGameDetails(Document page) {
        return page.select("header.game-package div.game-details").text();
    }

    public static String extractHeaderStatusDetails(Document page) {
        return page.select("header.game-package div.game-status span.status-detail").text();
    }

    public static Map<String, String> extractHomeAwayDetails(Element e) {
        Map<String, String> map = new HashMap<>();
        map.put("rank", e.select("span.rank").text());
        map.put("url", e.select("a.team-name").attr("href"));
        map.put("longName", e.select("a.team-name span.long-name").text());
        map.put("shortName", e.select("a.team-name span.short-name").text());
        map.put("abbrev", e.select("a.team-name span.abbrev").text());
        map.put("score", e.select("div.score").text());
        return map;
    }

    public static int gameDetailNumberOfPeriods(Document page) {
        return page.select("header.game-package div.game-status table#linescore tbody td").size() / 2 - 2;
    }

    public static String gameDetailLocation(Document page) {
        return page.select("div#gamepackage-game-information div.game-location").first().text();
    }

    public static String gameDetailTime(Document page) {
        return page.select(" div#gamepackage-game-information span.game-time").text();
    }

    public static String gameDetailDate(Document page) {
        return page.select(" div#gamepackage-game-information span.game-date").text();
    }

    public static String favorite(Document page) {
        List<String> lines = page.select("div#gamepackage-game-information div.odds li").eachText();
        for (String s : lines) {
            Matcher matcher = linePattern.matcher(s);
            if (matcher.matches()) return matcher.group(1);
        }
        return "";
    }
    public static Double line(Document page) {
        List<String> lines = page.select("div#gamepackage-game-information div.odds li").eachText();
        for (String s : lines) {
            Matcher matcher = linePattern.matcher(s);
            if (matcher.matches()) return Double.parseDouble(matcher.group(2));
        }
        return null;
    }

    public static Double overUnder(Document page) {
        List<String> lines = page.select("div#gamepackage-game-information div.odds li").eachText();
        for (String s : lines) {
            Matcher matcher = overUnderPattern.matcher(s);
            if (matcher.matches()) return Double.parseDouble(matcher.group(1));
        }
        return null;
    }


    private static List<Map<String, Object>> deepListExtract(Map<String, Object> map, List<String> keys) {
        if (keys.isEmpty()) throw new IllegalArgumentException();
        if (map.containsKey(keys.get(0))) {
            if (keys.size() == 1) {
                return (List) map.get(keys.get(0));
            } else {
                return deepListExtract((Map) map.get(keys.get(0)), keys.subList(1, keys.size()));
            }
        } else {
            return Collections.emptyList();
        }
    }

    public static List<String> extractTeamUrls(Document page) {
        return page.select("section.TeamLinks>a.AnchorLink").stream().map(e -> e.attr("href")).collect(Collectors.toList());
    }
}
