package com.fijimf.uberscraper.db.espn.repo;

import com.fijimf.uberscraper.db.espn.model.EspnScoreboardScrape;
import com.fijimf.uberscraper.db.espn.model.EspnSeasonScrape;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspnScoreboardScrapeRepo extends ReactiveCrudRepository<EspnScoreboardScrape, Long> {


}
