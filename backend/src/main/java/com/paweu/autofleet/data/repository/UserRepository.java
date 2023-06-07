package com.paweu.autofleet.data.repository;

import com.paweu.autofleet.data.models.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;


public interface UserRepository extends ReactiveCrudRepository<User, Long> {
    @Query("SELECT * FROM user WHERE email = $1")
    public Mono<User> findByEmail(String email);
}
