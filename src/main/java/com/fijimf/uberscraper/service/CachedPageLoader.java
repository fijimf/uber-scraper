package com.fijimf.uberscraper.service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Component
public class CachedPageLoader {
    public static final Logger logger = LoggerFactory.getLogger(CachedPageLoader.class);
    @Autowired
    private ChromeDriver chromeDriver;


    public Flux<Document> loadPages(Flux<String> urls) {
        return urls.flatMap(this::loadPage);
    }

    public Mono<Document> loadPage(Mono<String> url) {
        return url.flatMap(this::loadPage);
    }

    public Mono<Document> loadPage(String url) {
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
}
