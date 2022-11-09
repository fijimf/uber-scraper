package com.fijimf.uberscraper.service;

import com.fijimf.uberscraper.service.espn.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
public class ScrapingService {

    private final PageLoader pageLoader;
    private final EspnScraper espnScraper;

    public ScrapingService(PageLoader pageLoader, EspnScraper espnScraper) {
        this.pageLoader = pageLoader;
        this.espnScraper = espnScraper;
    }

    @GetMapping("/hello")
    public Map<String, String> teams() {
        Map<String, String> data = new HashMap<>();
        data.put("msg", "Hello");
        pageLoader.loadPage("https://www.espn.com").subscribe(System.err::println);
        return data;
    }

    @GetMapping("/hella")
    public Map<String, String> teams2() {
        Map<String, String> data = new HashMap<>();
        data.put("msg", "Hello");
        pageLoader.loadPageWithSelenium("https://www.espn.com").subscribe(System.err::println);
        return data;
    }

    @GetMapping("/confMap")
    public Map<String, String> confMap(@RequestParam("season") int season) {
        return pageLoader.loadPageWithSelenium(ConferenceScraper.allConferenceStandingsUrl(season)).map(ConferenceScraper::extractTeamConferenceMapping).block();
    }

    @GetMapping("/loadSeason")
    public Mono<Long> loadSeason(@RequestParam("season") int season,
                                 @RequestParam(value = "from", required = false) String from,
                                 @RequestParam(value = "to", required = false) String to) {
        LocalDate f = StringUtils.isBlank(from) ? null : LocalDate.parse(from, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate t = StringUtils.isBlank(to) ? null : LocalDate.parse(to, DateTimeFormatter.ISO_LOCAL_DATE);
        return espnScraper.loadSeason(season, f, t);
    }

    @GetMapping("/cancelLoader")
    public Mono<Long> cancelLoader(@RequestParam("id") long id) {
        return espnScraper.cancelLoader(id);
    }

    @GetMapping("/gameIds")
    public Mono<List<String>> gameIds(@RequestParam("yyyymmdd") String yyyymmdd) {
        String url = ScoreboardScraper.scoreboardUrl(Utils.yyyymmdd(yyyymmdd));
        return pageLoader.loadPageWithSelenium(url).map(ScoreboardScraper::extractGameIdsFromScoreboard);
    }


}
