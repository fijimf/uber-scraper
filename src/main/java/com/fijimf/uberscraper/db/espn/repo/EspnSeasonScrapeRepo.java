package com.fijimf.uberscraper.db.espn.repo;

import com.fijimf.uberscraper.db.espn.model.EspnSeasonScrape;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspnSeasonScrapeRepo extends ReactiveCrudRepository<EspnSeasonScrape, Long> {


}
