package com.fijimf.uberscraper.service.espn;

import com.fijimf.uberscraper.db.espn.model.EspnScoreboardScrape;
import com.fijimf.uberscraper.db.espn.model.EspnSeasonScrape;
import com.fijimf.uberscraper.db.espn.repo.EspnScoreboardScrapeRepo;
import com.fijimf.uberscraper.db.espn.repo.EspnSeasonScrapeRepo;
import com.fijimf.uberscraper.service.PageLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class EspnScraper {
    Logger logger = LoggerFactory.getLogger(EspnScraper.class);
    private final PageLoader pageLoader;
    private final EspnSeasonScrapeRepo essRepo;
    private final EspnScoreboardScrapeRepo escRepo;

    public EspnScraper(PageLoader pageLoader, EspnSeasonScrapeRepo essRepo, EspnScoreboardScrapeRepo escRepo) {
        this.pageLoader = pageLoader;
        this.essRepo = essRepo;
        this.escRepo = escRepo;
    }

    public Mono<Long> loadSeason(int season) {
        return essRepo
                .save(new EspnSeasonScrape(0L, season, LocalDateTime.now(), null, null))
                .doOnNext(this::doLoadSeason2)
                .map(EspnSeasonScrape::getId);
//                .flatMap(ss ->
//                Flux
//                .fromIterable(Utils.seasonToDates(season))
//                .map(ScoreboardScraper::scoreboardUrl)
//                .flatMap(pageLoader::loadPageWithSelenium)
//                .flatMapIterable(ScoreboardScraper::extractGameIdsFromScoreboard)
//                .map(GameScraper::gameUrl)
//                .flatMap(pageLoader::loadPageWithSelenium)
//                .flatMap(d -> Mono.justOrEmpty(GameScraper.extractGameDetails(d))));
    }

private Mono<EspnScoreboardScrape> createScoreboardScrape(Long parentId, LocalDate d){
    String url = ScoreboardScraper.scoreboardUrl(d);
    return escRepo.save(new EspnScoreboardScrape(parentId, d, url))
            .doOnNext(e -> logger.info("Saved scoreboard scraoe rec "+e.getId()+"/"+e.getScoreboardKey()));
}
    private void doLoadSeason2(EspnSeasonScrape seas) {
        Utils.generateSeasonDates(seas.getSeason())
                .flatMap(d ->createScoreboardScrape(seas.getId(), d))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();
    }
}

