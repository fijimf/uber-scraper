package com.fijimf.uberscraper.db.user.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "auth_token")

public class AuthToken {
    @Id
    private long id;

    @Column(  "user_id")
    private long userId;

    @Column(  "token")
    private String token;
    @Column(  "expires_at")
    private LocalDateTime expiresAt;

    public AuthToken() {
    }

    public AuthToken(long userId, String token, LocalDateTime expiresAt) {
        this.id = 0L;
        this.userId = userId;
        this.token = token;
        this.expiresAt = expiresAt;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

