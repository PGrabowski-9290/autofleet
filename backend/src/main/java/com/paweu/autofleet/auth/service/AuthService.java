package com.paweu.autofleet.auth.service;

import com.paweu.autofleet.auth.response.LoginResponse;
import com.paweu.autofleet.auth.response.RegisterResponse;
import com.paweu.autofleet.data.models.User;
import com.paweu.autofleet.data.service.UserService;
import com.paweu.autofleet.exceptions.ResponseException;
import com.paweu.autofleet.service.JwtService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Mono<ResponseEntity<LoginResponse>> login(@NonNull String email, @NonNull String password){
        return userService.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> {
                    ResponseCookie refCookie = ResponseCookie.from("jwt", jwtService.generateRefreshToken(email))
                            .httpOnly(true)
                            .maxAge(jwtService.getRefreshTokenExpires())
                            .build();
                    String accessToken = jwtService.generateAccessToken(email);
                    return ResponseEntity.ok().header("Set-Cookie", refCookie.toString()).body(new LoginResponse(accessToken));
                })
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    public Mono<ResponseEntity<RegisterResponse>> register(String email, String password, String username){
        return Mono.just(new User(
                email,
                passwordEncoder.encode(password),
                username
            ))
            .flatMap(userService::save)
                .map(user -> ResponseEntity.ok().body(
                        new RegisterResponse(
                                "regisetered",
                                jwtService.generateAccessToken(user.getEmail())
                        )
                ));
    }
}
