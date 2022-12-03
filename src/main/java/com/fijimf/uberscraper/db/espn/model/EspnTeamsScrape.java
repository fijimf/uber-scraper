package com.fijimf.uberscraper.db.espn.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "espn_teams_scrape")
public class EspnTeamsScrape {
        @Id
        private long id;
        private String url;
        @Column("retrieved_at")
        private LocalDateTime retrievedAt;
        @Column("response_time_ms")
        private Long responseTimeMs;
        @Column("response_code")
        private Integer responseCode;

        private String response;
        private String status;

    public EspnTeamsScrape() {
    }

    public EspnTeamsScrape(long id, String url, LocalDateTime retrievedAt, Long responseTimeMs, Integer responseCode, String response, String status) {
        this.id = id;
        this.url = url;
        this.retrievedAt = retrievedAt;
        this.responseTimeMs = responseTimeMs;
        this.responseCode = responseCode;
        this.response = response;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getRetrievedAt() {
        return retrievedAt;
    }

    public void setRetrievedAt(LocalDateTime retrievedAt) {
        this.retrievedAt = retrievedAt;
    }

    public Long getResponseTimeMs() {
        return responseTimeMs;
    }

    public void setResponseTimeMs(Long responseTimeMs) {
        this.responseTimeMs = responseTimeMs;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
