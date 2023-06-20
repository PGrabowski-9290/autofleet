package com.paweu.autofleet.auth.service;

import com.paweu.autofleet.auth.response.LoginResponse;
import com.paweu.autofleet.auth.response.LogoutResponse;
import com.paweu.autofleet.auth.response.RegisterResponse;
import com.paweu.autofleet.data.models.User;
import com.paweu.autofleet.data.repository.UserRepository;
import com.paweu.autofleet.security.SecurityUserDetails;
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
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Mono<ResponseEntity<LoginResponse>> login(@NonNull String email, @NonNull String password){
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .flatMap(this::updateUserJWT)
                .map(this::generateLoginResponse)
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    public Mono<ResponseEntity<RegisterResponse>> register(String email, String password, String username){
        return Mono.just(new User(
                email,
                passwordEncoder.encode(password),
                username
            ))
            .flatMap(userRepository::save)
                .map(user -> ResponseEntity.ok().body(
                        new RegisterResponse(
                                "regisetered",
                                jwtService.generateAccessToken(user.getEmail())
                        )
                ));
    }

    public Mono<ResponseEntity<LoginResponse>> refresh(String cookieJwt){
        return Mono.just(jwtService.validateRefresh(cookieJwt))
                .flatMap(email -> userRepository.findByEmailAndRefToken(email,cookieJwt))
                .flatMap(user -> {
                    user.setRefToken(jwtService.generateRefreshToken(user.getEmail()));
                    return updateUserJWT(user);
                })
                .map(this::generateLoginResponse)
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    public Mono<ResponseEntity<LogoutResponse>> logout(SecurityUserDetails auth){
        return userRepository.findByEmail(auth.getUsername())
                .flatMap(user -> {
                    user.setRefToken("");
                    return updateUserJWT(user);
                })
                .map(user -> ResponseEntity.ok().
                        header("Set-Cookie", generateCookie("", 0))
                        .body(new LogoutResponse("wylogowano")));
    }

    private Mono<User> updateUserJWT(User user){
        return userRepository.updateJWT(user.getRefToken(), user.getUserId());
    }

    private ResponseEntity<LoginResponse> generateLoginResponse(User user){
        String refCookie = generateCookie(user.getRefToken(),jwtService.getRefreshTokenExpires());
        String accessToken = jwtService.generateAccessToken(user.getEmail());
        return ResponseEntity.ok()
                .header("Set-Cookie", refCookie)
                .body(new LoginResponse(accessToken, user.getUsername()));
    }

    private String generateCookie(String value, long age){
        return ResponseCookie.from("jwt", value)
                .httpOnly(true)
                .maxAge(age)
                .build().toString();
    }
}