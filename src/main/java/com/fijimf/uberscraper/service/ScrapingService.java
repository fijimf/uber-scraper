package com.fijimf.uberscraper.service;

import com.fijimf.uberscraper.service.espn.Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.*;

@RestController
public class ScrapingService {

    private final PageLoader pageLoader;


    public ScrapingService(PageLoader pageLoader) {
        this.pageLoader = pageLoader;
    }

    @GetMapping("/hello")
    public Map<String, String> teams()  {
        Map<String, String> data = new HashMap<>();
        data.put("msg", "Hello");
        pageLoader.loadPage("https://www.espn.com").subscribe(System.err::println);
        return data;
    }

    @GetMapping("/hella")
    public Map<String, String> teams2()  {
        Map<String, String> data = new HashMap<>();
        data.put("msg", "Hello");
        pageLoader.loadPageWithSelenium("https://www.espn.com").subscribe(System.err::println);
        return data;
    }
    @GetMapping("/confMap")
    public Map<String, String> confMap(@RequestParam("season") int season)  {
        return pageLoader.loadPageWithSelenium(Utils.allConferenceStandingsUrl(season)).map(Utils::extractTeamConferenceMapping).block();
    }

    @GetMapping("/gameIds")
    public Mono<List<String>> gameIds(@RequestParam("yyyymmdd") String yyyymmdd)  {
        String url = Utils.scoreboardUrl(Utils.yyyymmdd(yyyymmdd));
        return pageLoader.loadPageWithSelenium(url).map(Utils::extractGameIdsFromScoreboard);
    }


}
