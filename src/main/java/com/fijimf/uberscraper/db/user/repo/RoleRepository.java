package com.fijimf.uberscraper.db.user.repo;

import com.fijimf.uberscraper.db.user.model.Role;
import com.fijimf.uberscraper.db.user.model.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Optional;
@Repository
public interface RoleRepository extends ReactiveCrudRepository<Role, Long> {
    @Query("select * from role a where a.role_name = :roleName ")
    Flux<Role> findByRoleName(String roleName);
}
