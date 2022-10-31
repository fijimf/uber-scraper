package com.fijimf.uberscraper.service.espn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fijimf.uberscraper.model.espn.Scoreboard;
import com.fijimf.uberscraper.model.espn.ScoreboardGame;
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
    private final static String SCOREBOARD_WITH_ODDS="https://site.web.api.espn.com/apis/v2/scoreboard/header?sport=basketball&league=mens-college-basketball&dates=20210403";
    private final static String SCOREBOARD_WITH_SITE="https://site.web.api.espn.com/apis/site/v2/sports/basketball/mens-college-basketball/scoreboard?dates=20210403&group=50";

    public static List<ScoreboardGame> extractGameDetails(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Scoreboard scoreboard = objectMapper.readValue(json, Scoreboard.class);
        return scoreboard.getSports().get(0).getLeagues().get(0).getEvents();
    }

}
