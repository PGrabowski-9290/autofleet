package com.paweu.autofleet.auth.service;

import com.paweu.autofleet.auth.response.ResponseLogin;
import com.paweu.autofleet.auth.response.ResponseLogout;
import com.paweu.autofleet.auth.response.ResponseRegister;
import com.paweu.autofleet.data.models.User;
import com.paweu.autofleet.data.repository.UserRepository;
import com.paweu.autofleet.error.exceptions.UserAlreadyExistException;
import com.paweu.autofleet.error.exceptions.WrongCredentialsException;
import com.paweu.autofleet.security.SecurityUserDetails;
import com.paweu.autofleet.service.JwtService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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

    public Mono<ResponseEntity<ResponseLogin>> login(@NonNull String email, @NonNull String password) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .flatMap(user -> {
                    user.setRefToken(jwtService.generateRefreshToken(user.getEmail()));
                    return userRepository.save(user);
                })
                .map(this::generateLoginResponse)
                .switchIfEmpty(Mono.error(WrongCredentialsException::new));
    }

    public Mono<ResponseEntity<ResponseRegister>> register(String email, String password, String username) {
        return isUserExist(email)
                .filter(userExist -> !userExist)
                .switchIfEmpty(Mono.error(UserAlreadyExistException::new))
                .map(aBoolean -> User.builder()
                        .email(email)
                        .password(passwordEncoder.encode(password))
                        .username(username)
                        .refToken(jwtService.generateRefreshToken(email))
                        .build())
                .flatMap(userRepository::save)
                .map(user -> ResponseEntity.status(HttpStatus.CREATED)
                        .header(HttpHeaders.SET_COOKIE, generateCookie(user.getRefToken(), jwtService.getRefreshTokenExpires()))
                        .body(
                                new ResponseRegister(
                                        "Registered",
                                        jwtService.generateAccessToken(user.getEmail())
                                )
                        ));
    }

    public Mono<ResponseEntity<ResponseLogin>> refresh(String cookieJwt) {
        return Mono.just(jwtService.validateRefresh(cookieJwt))
                .flatMap(email -> userRepository.findByEmailAndRefToken(email, cookieJwt))
                .cast(User.class)
                .flatMap(user -> {
                    user.setRefToken(jwtService.generateRefreshToken(user.getEmail()));
                    return userRepository.save(user);
                })
                .map(this::generateLoginResponse)
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    public Mono<ResponseEntity<ResponseLogout>> logout(SecurityUserDetails auth) {
        return userRepository.findByEmail(auth.getUsername())
                .flatMap(user -> {
                    user.setRefToken("");
                    return userRepository.save(user);
                })
                .map(user -> ResponseEntity.status(HttpStatus.OK)
                        .header(HttpHeaders.SET_COOKIE, generateCookie("", 0))
                        .body(new ResponseLogout("Logged Out")));
    }

    private ResponseEntity<ResponseLogin> generateLoginResponse(User user) {
        String refCookie = generateCookie(user.getRefToken(), jwtService.getRefreshTokenExpires());
        String accessToken = jwtService.generateAccessToken(user.getEmail());
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, refCookie)
                .body(new ResponseLogin(accessToken, user.getUsername()));
    }

    private String generateCookie(String value, long age) {
        return ResponseCookie.from("jwt", value)
                .httpOnly(true)
                .maxAge(age)
                .build().toString();
    }

    private Mono<Boolean> isUserExist(String email) {
        return userRepository.findByEmail(email)
                .map(user -> true)
                .switchIfEmpty(Mono.just(false));
    }
}