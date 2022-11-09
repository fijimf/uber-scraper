package com.fijimf.uberscraper.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Component
public class PageLoader {
    public static final Logger logger = LoggerFactory.getLogger(PageLoader.class);
    @Autowired
    private ChromeDriver chromeDriver;

    private RestTemplate restTemplate = new RestTemplate();

    public Mono<Document> loadPageWithSelenium(String url) {
        String urlKey = DigestUtils.md5Hex(url);
        logger.info(urlKey + "-" + url);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("GET " + url);
       // chromeDriver.get(url);
        chromeDriver.navigate().to(url);
        String pageSource = chromeDriver.getPageSource();
        stopWatch.stop();
        logger.info(pageSource.length() + " bytes received in " + stopWatch.getTotalTimeSeconds() + " seconds");
        return Mono.just(Jsoup.parse(pageSource));
    }

    public Mono<Document> loadPageWithSelenium(Mono<String> url) {
        return url.flatMap(this::loadPageWithSelenium);
    }

    public Flux<Document> loadPagesWithSelenium(Flux<String> urls) {
        return urls.concatMap(this::loadPageWithSelenium);
    }

    public Flux<Document> loadPages(Flux<String> urls) {
        return urls.flatMap(this::loadPage);
    }

    public Mono<Document> loadPage(Mono<String> url) {
        return url.flatMap(this::loadPage);
    }

    public Mono<Document> loadPage(String url) {
        logger.info(url);
        Connection connection = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/103.0.0.0 Safari/537.36")
                .referrer("www.google.com");
        try {
            return Mono.just(connection.get());
        } catch (IOException e) {
            logger.error("Exception retrieving url {}", url);
            return Mono.empty();
        }
    }

    public <T> ResponseEntity<T> loadX(String url, Class<T> type){
       return restTemplate.getForEntity(url, type);
    }
}
