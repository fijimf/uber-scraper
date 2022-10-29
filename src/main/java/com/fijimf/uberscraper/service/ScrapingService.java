package com.fijimf.uberscraper.service;

import com.fijimf.uberscraper.service.espn.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<Long> loadSeason(@RequestParam("season") int season) {
        return espnScraper.loadSeason(season);
    }

    @GetMapping("/gameIds")
    public Mono<List<String>> gameIds(@RequestParam("yyyymmdd") String yyyymmdd) {
        String url = ScoreboardScraper.scoreboardUrl(Utils.yyyymmdd(yyyymmdd));
        return pageLoader.loadPageWithSelenium(url).map(ScoreboardScraper::extractGameIdsFromScoreboard);
    }


}
