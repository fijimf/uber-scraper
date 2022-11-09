package com.fijimf.uberscraper.service.espn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fijimf.uberscraper.db.espn.model.EspnScoreboardScrape;
import com.fijimf.uberscraper.db.espn.model.EspnSeasonScrape;
import com.fijimf.uberscraper.db.espn.repo.EspnScoreboardScrapeRepo;
import com.fijimf.uberscraper.db.espn.repo.EspnSeasonScrapeRepo;
import com.fijimf.uberscraper.model.espn.Scoreboard;
import com.fijimf.uberscraper.service.PageLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class EspnScraper {
    Logger logger = LoggerFactory.getLogger(EspnScraper.class);
    private final PageLoader pageLoader;
    private final EspnSeasonScrapeRepo essRepo;
    private final EspnScoreboardScrapeRepo escRepo;
    private final ObjectMapper mapper;

    private final ConcurrentMap<Long, Disposable> runningLoaders = new ConcurrentHashMap<>();
    private final static String URL_SCOREBOARD_WITH_ODDS_TEMPLATE = "https://site.web.api.espn.com/apis/v2/scoreboard/header?sport=basketball&" +
            "league=mens-college-basketball&" +
            "dates=%s&" +
            "groups=50&" +
            "limit=300";

    public EspnScraper(PageLoader pageLoader, EspnSeasonScrapeRepo essRepo, EspnScoreboardScrapeRepo escRepo, ObjectMapper mapper) {
        this.pageLoader = pageLoader;
        this.essRepo = essRepo;
        this.escRepo = escRepo;
        this.mapper = mapper;
    }

    private static String scoreboardWithOddsUrl(LocalDate date) {
        return URL_SCOREBOARD_WITH_ODDS_TEMPLATE.formatted(date.format(DateTimeFormatter.BASIC_ISO_DATE));
    }

    public Mono<Long> loadSeason(int season, LocalDate from, LocalDate to) {
        return essRepo
                .save(new EspnSeasonScrape(0L, season, from, to, LocalDateTime.now(), null, "RUNNING"))
                .doOnNext(s -> runningLoaders.put(s.getId(), loadSeason(s)))
                .map(EspnSeasonScrape::getId);
    }

    private Disposable loadSeason(EspnSeasonScrape seas) {
        return generateSeasonDates(seas)
                .delayElements(Duration.ofSeconds(60))
                .flatMap(d -> createScoreboardScrape(seas.getId(), d))
                .subscribeOn(Schedulers.boundedElastic())
                .then(essRepo.save(seas.success()))
                .onErrorResume(e->essRepo.save(seas.error()))
                .log()
                .subscribe();
    }

    private EspnScoreboardScrape loadJsonScoreboard(EspnScoreboardScrape s) {
        LocalDateTime start = LocalDateTime.now();
        logger.info("Loading " + s.getUrl());

        try {
            Class<?> flavor = Class.forName(s.getFlavor());
            ResponseEntity<?> entity = pageLoader.loadX(s.getUrl(), flavor);
            logger.info(entity.getStatusCode().toString() + " | " + entity.hasBody());
            long elapsed = ChronoUnit.MILLIS.between(start, LocalDateTime.now());
            s.setResponseCode(entity.getStatusCodeValue());
            s.setResponseTimeMs(elapsed);
            s.setRetrievedAt(start);
            if (entity.hasBody() && entity.getBody() != null) {
                Scoreboard scoreboard = (Scoreboard) entity.getBody();
                try {
                    s.setResponse(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(scoreboard));
                    s.setNumberOfGames((int) scoreboard.numberOfGames());
                } catch (JsonProcessingException e) {
                    logger.error("Error parsing response on " + s.getScoreboardKey(), e);
                    s.setResponse(null);
                    s.setNumberOfGames(0);
                }
            }
        } catch (HttpStatusCodeException e){
            long elapsed = ChronoUnit.MILLIS.between(start, LocalDateTime.now());
            s.setResponseCode(e.getRawStatusCode());
            s.setResponseTimeMs(elapsed);
            s.setRetrievedAt(start);
            s.setResponse(null);
            s.setNumberOfGames(-1);
            logger.error("Exception retrieving scoreboard for "+s.getScoreboardKey(), e);
        } catch (Exception e) {
            long elapsed = ChronoUnit.MILLIS.between(start, LocalDateTime.now());
            s.setResponseCode(999);
            s.setResponseTimeMs(elapsed);
            s.setRetrievedAt(start);
            s.setResponse(null);
            s.setNumberOfGames(-1);
            logger.error("Exception retrieving scoreboard for "+s.getScoreboardKey(), e);
        }
        return s;
    }

    private Mono<EspnScoreboardScrape> createScoreboardScrape(Long parentId, LocalDate d) {
        String url = scoreboardWithOddsUrl(d);
        return escRepo.save(new EspnScoreboardScrape(parentId, d, Scoreboard.class.getCanonicalName(), url))
                .map(this::loadJsonScoreboard)
                .flatMap(escRepo::save)
                .doOnNext(e -> logger.info("Saved scoreboard scrape rec " + e.getId() + "/" + e.getScoreboardKey()));
    }


    public Mono<Long> cancelLoader(long id) {
        return essRepo.findById(id)
                .filter(s -> s.getCompletedAt() == null)
                .map(EspnSeasonScrape::cancel)
                .flatMap(essRepo::save)
                .map(s -> {
                    Disposable disposable = runningLoaders.get(id);
                    if (disposable != null) {
                        logger.info("Cancelling scrape " + id);
                        disposable.dispose();
                        runningLoaders.remove(id);
                        return s.getId();
                    } else {
                        logger.info("Attempt to cancel " + id + ".  Id was not active");
                        return 0L;
                    }
                });
    }

    public Flux<LocalDate> generateSeasonDates(EspnSeasonScrape s) {
        return Flux.from(getAlreadyScrapedDays(s)).flatMap(ds -> Flux.generate(s::fromDate,
                (state, sink) -> {
                    int i = 0;
                    while (ds.contains(state.plusDays(i))) {
                        logger.info("Skipping scrape of " + state.plusDays(i) + " as it was already scraped.");
                        i++;
                    }
                    if (state.plusDays(i).isAfter(s.toDate())) {
                        sink.complete();
                    } else {
                        sink.next(state.plusDays(i));
                    }
                    return state.plusDays(i + 1);
                }));
    }

    public Mono<Set<LocalDate>> getAlreadyScrapedDays(EspnSeasonScrape s) {
        return escRepo
                .alreadyScrapedDates(s.getSeason())
                .collectList()
                .map(HashSet::new);
    }

}

