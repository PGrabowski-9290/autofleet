package com.paweu.autofleet.auth.service;

import com.paweu.autofleet.auth.response.LoginResponse;
import com.paweu.autofleet.data.repository.UserRepository;
import com.paweu.autofleet.service.JwtService;
import lombok.NonNull;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Log log;

    public Mono<ResponseEntity<LoginResponse>> login(@NonNull String email, @NonNull String password){
        return userRepository.findByEmail(email)
                .onErrorResume(throwable -> {
                    return  Mono.error(new IllegalArgumentException("błąd"));
                })
                .handle((user, sink) -> {
                    if (!passwordEncoder.matches(password, user.getPassword())) {
                        sink.error(new IllegalArgumentException("bad password"));
                    }
                    ResponseCookie refCookie = ResponseCookie.from("jwt", jwtService.generateRefreshToken(email))
                            .httpOnly(true)
                            .maxAge(jwtService.getRefreshTokenExpires())
                            .build();
                    String accessToken = jwtService.generateAccessToken(email);

                    sink.next(ResponseEntity.ok().header("Set-Cookie", refCookie.toString()).body(new LoginResponse(accessToken)));
                });
    }
}
