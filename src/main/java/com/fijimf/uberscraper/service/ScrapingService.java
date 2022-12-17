package com.fijimf.uberscraper.service;

import com.fijimf.uberscraper.db.espn.model.EspnSeasonScrape;
import com.fijimf.uberscraper.service.espn.*;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/api/scraper")
public class ScrapingService {

    private final PageLoader pageLoader;
    private final EspnScraper espnScraper;

    public ScrapingService(PageLoader pageLoader, EspnScraper espnScraper) {
        this.pageLoader = pageLoader;
        this.espnScraper = espnScraper;
    }

    @GetMapping("/seasons/status")
    public Mono<List<SeasonStatus>> seasonsStatus() {
        throw new NotImplementedException();
    }

    @PostMapping("/seasons/create")
    public Mono<SeasonStatus> createSeason(@RequestParam(value = "season", required = true) int season) {
        throw new NotImplementedException();
    }

    @PostMapping("/seasons/delete")
    public Mono<SeasonStatus> deleteSeason(@RequestParam(value = "season", required = true) int season) {
        throw new NotImplementedException();
    }

    @GetMapping("/conferences/status")
    public Mono<SeasonStatus> statusConferences(@RequestParam(value = "season", required = true) int season) {
        throw new NotImplementedException();
    }

    @PostMapping("/conferences/load")
    public Mono<SeasonStatus> loadConferences(@RequestParam(value = "season", required = true) int season) {
        throw new NotImplementedException();
    }

    @PostMapping("/conferences/publish")
    public Mono<SeasonStatus> publishConferences(@RequestParam(value = "season", required = true) int season) {
        throw new NotImplementedException();
    }

    @PostMapping("/conferences/clear")
    public Mono<SeasonStatus> clearConferences(@RequestParam(value = "season", required = true) int season) {
        throw new NotImplementedException();
    }

    @GetMapping("/teams/status")
    public Mono<SeasonStatus> statusTeams(@RequestParam(value = "season", required = true) int season) {
        throw new NotImplementedException();
    }

    @PostMapping("/teams/load")
    public Mono<SeasonStatus> loadTeams(@RequestParam(value = "season", required = true) int season) {
        throw new NotImplementedException();
    }

    @PostMapping("/teams/publish")
    public Mono<SeasonStatus> publishTeams(@RequestParam(value = "season", required = true) int season) {
        throw new NotImplementedException();
    }

    @PostMapping("/teams/clear")
    public Mono<SeasonStatus> clearTeams(@RequestParam(value = "season", required = true) int season) {
        throw new NotImplementedException();
    }
    @GetMapping("/conferenceMaps/status")
    public Mono<SeasonStatus> statusConferenceMaps(@RequestParam(value = "season", required = true) int season) {
        throw new NotImplementedException();
    }

    @PostMapping("/conferenceMaps/load")
    public Mono<SeasonStatus> loadConferenceMaps(@RequestParam(value = "season", required = true) int season) {
        throw new NotImplementedException();
    }

    @PostMapping("/conferenceMaps/publish")
    public Mono<SeasonStatus> publishConferenceMaps(@RequestParam(value = "season", required = true) int season) {
        throw new NotImplementedException();
    }

    @PostMapping("/conferenceMaps/clear")
    public Mono<SeasonStatus> clearConferenceMaps(@RequestParam(value = "season", required = true) int season) {
        throw new NotImplementedException();
    }

    @GetMapping("/loadSeason")
    public Mono<Long> loadSeason(@RequestParam("season") int season,
                                 @RequestParam(value = "from", required = false) String from,
                                 @RequestParam(value = "to", required = false) String to) {
        LocalDate f = StringUtils.isBlank(from) ? null : LocalDate.parse(from, DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate t = StringUtils.isBlank(to) ? null : LocalDate.parse(to, DateTimeFormatter.BASIC_ISO_DATE);
        return espnScraper.loadSeason(season, f, t);
    }

    @GetMapping("/publishSeason")
    public Mono<Long> publishSeason(@RequestParam("season") int season,
                                    @RequestParam(value = "from", required = false) String from,
                                    @RequestParam(value = "to", required = false) String to) {
        LocalDate f = StringUtils.isBlank(from) ? null : LocalDate.parse(from, DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate t = StringUtils.isBlank(to) ? null : LocalDate.parse(to, DateTimeFormatter.BASIC_ISO_DATE);
        return espnScraper.loadSeason(season, f, t);
    }

    @GetMapping("/runningLoaders")
    public Flux<EspnSeasonScrape> showLoaders() {
        return espnScraper.showLoaders();
    }

    @GetMapping("/cancelLoader")
    public Mono<Long> cancelLoader(@RequestParam("id") long id) {
        return espnScraper.cancelLoader(id);
    }

    @GetMapping("/loadTeams")
    public Mono<Long> loadTeams() {
        return espnScraper.loadTeams();
    }

}
