package com.fijimf.uberscraper.service;

import com.fijimf.uberscraper.service.espn.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/loadSeason")
    public Mono<Long> loadSeason(@RequestParam("season") int season,
                                 @RequestParam(value = "from", required = false) String from,
                                 @RequestParam(value = "to", required = false) String to) {
        LocalDate f = StringUtils.isBlank(from) ? null : LocalDate.parse(from, DateTimeFormatter.BASIC_ISO_DATE);
        LocalDate t = StringUtils.isBlank(to) ? null : LocalDate.parse(to, DateTimeFormatter.BASIC_ISO_DATE);
        return espnScraper.loadSeason(season, f, t);
    }

    @GetMapping("/cancelLoader")
    public Mono<Long> cancelLoader(@RequestParam("id") long id) {
        return espnScraper.cancelLoader(id);
    }
}
