package com.fijimf.uberscraper.db.user.repo;

import com.fijimf.uberscraper.db.user.model.AuthToken;
import com.fijimf.uberscraper.db.user.model.Role;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Optional;

public interface AuthTokenRepository extends ReactiveCrudRepository<AuthToken, Long> {

    @Query("select * from auth_token a where a.auth_token = :auth_token ")
    Flux<AuthToken> findByToken(String token);
    @Query("select * from auth_token a where a.user_id = :user_id ")
    Flux<AuthToken> findAllByUserId(long userId);
}
