package com.paweu.autofleet.auth;

import com.paweu.autofleet.auth.request.LoginRequest;
import com.paweu.autofleet.auth.request.RegisterRequest;
import com.paweu.autofleet.auth.response.LoginResponse;
import com.paweu.autofleet.auth.response.LogoutResponse;
import com.paweu.autofleet.auth.response.RegisterResponse;
import com.paweu.autofleet.auth.service.AuthService;
import com.paweu.autofleet.security.SecurityUserDetails;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public Mono<ResponseEntity<RegisterResponse>> register(@RequestBody @Valid RegisterRequest data){
        return authService.register(data.email(), data.password(), data.username());
    }

    @Override
    public Mono<ResponseEntity<LoginResponse>> login(@RequestBody @Valid LoginRequest data){
        return authService.login(data.email(), data.password());
    }

    @Override
    public Mono<ResponseEntity<LoginResponse>> refresh(@CookieValue(name = "jwt") String cookie){
        return authService.refresh(cookie);
    }

    @Override
    public Mono<ResponseEntity<LogoutResponse>> logout(@Parameter(hidden = true) @AuthenticationPrincipal SecurityUserDetails auth){
        return authService.logout(auth);
    }
}
