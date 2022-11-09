package com.fijimf.uberscraper.db.espn.repo;

import com.fijimf.uberscraper.db.espn.model.EspnScoreboardScrape;
import com.fijimf.uberscraper.db.espn.model.EspnSeasonScrape;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface EspnScoreboardScrapeRepo extends ReactiveCrudRepository<EspnScoreboardScrape, Long> {

    @Query("select distinct s.scoreboard_key from espn_scoreboard_scrape s join espn_season_scrape e on s.espn_season_scrape_id = e.id where e.season = :season and s.response_code=200")
    Flux<LocalDate> alreadyScrapedDates(int year);
}
