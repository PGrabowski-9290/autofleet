package com.paweu.autofleet.auth;

import com.paweu.autofleet.auth.request.LoginRequest;
import com.paweu.autofleet.auth.request.RegisterRequest;
import com.paweu.autofleet.auth.response.LoginResponse;
import com.paweu.autofleet.auth.response.RegisterResponse;
import com.paweu.autofleet.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/refresh")
    public Mono<ResponseEntity<LoginResponse>> refresh(){
        return null;
    }
}
