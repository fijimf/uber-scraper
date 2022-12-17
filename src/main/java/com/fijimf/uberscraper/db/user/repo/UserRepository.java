package com.fijimf.uberscraper.db.user.repo;

import com.fijimf.uberscraper.db.user.model.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    @Query("select * from user u where u.user_name = :userName ")
    Flux<User> findByUserName(String userName);
    @Query("select * from user u where u.email = :email ")
    Flux<User> findByEmail(String email);
}
