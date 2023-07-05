package com.paweu.autofleet.data.repository;

import com.paweu.autofleet.data.models.User;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User,String> {

    @Query("SELECT * FROM public.user WHERE email = $1;")
    Mono<User> findByEmail(String email);

    @Query("Select * FROM public.user where public.user.email = $1 and public.user.reftoken = $2")
    Mono<User> findByEmailAndRefToken(String email, String cookieJwt);

}
