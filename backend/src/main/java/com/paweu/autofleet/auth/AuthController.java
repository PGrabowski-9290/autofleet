package com.paweu.autofleet.auth;

import com.paweu.autofleet.auth.request.LoginRequest;
import com.paweu.autofleet.auth.request.RegisterRequest;
import com.paweu.autofleet.auth.response.LoginResponse;
import com.paweu.autofleet.auth.response.RegisterResponse;
import com.paweu.autofleet.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public Mono<ResponseEntity<RegisterResponse>> register(@RequestBody RegisterRequest data){
        return authService.register(data.email(), data.password(), data.username());
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<LoginResponse>> login(@RequestBody LoginRequest data){
        return authService.login(data.email(), data.password());
    }

    @GetMapping("/refresh")
    public Mono<ResponseEntity<LoginResponse>> refresh(@CookieValue(name = "jwt") String cookie){
        return authService.refresh(cookie);
    }
}
