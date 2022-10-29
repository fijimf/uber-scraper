package com.fijimf.uberscraper.service.espn;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.fijimf.uberscraper.service.espn.Utils.ESPN_BASE;

public class GameScraper {
    private static final Pattern linePattern = Pattern.compile("Line: ([A-Z]+) (-\\d+\\.\\d)");
    private static final Pattern overUnderPattern = Pattern.compile("Over/Under: (\\d+\\.\\d)");

    public static String gameUrl(int gameId) {
        return ESPN_BASE + "/mens-college-basketball/game/_/gameId/" + gameId;
    }
    public static String gameUrl(String gameId) {
        return ESPN_BASE + "/mens-college-basketball/game/_/gameId/" + gameId;
    }

    public static Optional<RawGameDetails> extractGameDetails(Document page) {
        Optional<Element> homeDiv = Optional.ofNullable(page.select("header.game-package div.competitors div.home").first());
        return homeDiv.flatMap(d-> {
            Map<String, String> homeData = GameScraper.homeAwayDetails(d);
            Optional<Element> awayDiv = Optional.ofNullable(page.select("header.game-package div.competitors div.away").first());
            return awayDiv.flatMap(e -> {
                Map<String, String> awayData = GameScraper.homeAwayDetails(e);
                return date(page).flatMap(f -> {
                    return time(page).flatMap(g -> {
                        return Optional.of(new RawGameDetails(
                                GameScraper.gameDetails(page),
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
                                GameScraper.numOfPeriods(page),
                                GameScraper.statusDetails(page),
                                f,
                                g,
                                GameScraper.location(page),
                                GameScraper.favorite(page),
                                GameScraper.line(page),
                                GameScraper.overUnder(page))
                        );
                    });
                });
            });
        });
    }

    public static String gameDetails(Document page) {
        return page.select("header.game-package div.game-details").text();
    }

    public static String statusDetails(Document page) {
        return page.select("header.game-package div.game-status span.status-detail").text();
    }

    public static Map<String, String> homeAwayDetails(Element e) {
        Map<String, String> map = new HashMap<>();
        map.put("rank", e.select("span.rank").text());
        map.put("url", e.select("a.team-name").attr("href"));
        map.put("longName", e.select("a.team-name span.long-name").text());
        map.put("shortName", e.select("a.team-name span.short-name").text());
        map.put("abbrev", e.select("a.team-name span.abbrev").text());
        map.put("score", e.select("div.score").text());
        return map;
    }

    public static int numOfPeriods(Document page) {
        return page.select("header.game-package div.game-status table#linescore tbody td").size() / 2 - 2;
    }

    public static String location(Document page) {
        return page.select("div#gamepackage-game-information div.game-location").first().text();
    }

    public static Optional<LocalDateTime> time(Document page) {
        String dateStr = page.select(" div#gamepackage-game-information span.game-date").text();
        String timeStr = page.select(" div#gamepackage-game-information span.game-time").text();
        if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(timeStr)) {
            return Optional.empty();
        } else {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy h:mm a");
                return Optional.of(LocalDateTime.parse(dateStr + " " + timeStr.replace(" ET", ""), formatter));
            } catch (Exception e) {
                return Optional.empty();
            }
        }
    }

    public static Optional<LocalDate> date(Document page) {
        String dateStr = page.select(" div#gamepackage-game-information span.game-date").text();
        if (StringUtils.isBlank(dateStr)) {
            return Optional.empty();
        } else {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy");
                return Optional.of(LocalDate.parse(dateStr, formatter));
            } catch (Exception e) {
                return Optional.empty();
            }
        }
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
}
