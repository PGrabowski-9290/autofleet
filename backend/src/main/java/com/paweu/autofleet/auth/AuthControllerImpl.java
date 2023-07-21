package com.paweu.autofleet.auth;

import com.paweu.autofleet.auth.request.RequestLogin;
import com.paweu.autofleet.auth.request.RequestRegister;
import com.paweu.autofleet.auth.response.ResponseLogin;
import com.paweu.autofleet.auth.response.ResponseLogout;
import com.paweu.autofleet.auth.response.ResponseRegister;
import com.paweu.autofleet.auth.service.AuthService;
import com.paweu.autofleet.security.SecurityUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public Mono<ResponseEntity<ResponseRegister>> register(@Valid RequestRegister data){
        return authService.register(data.email(), data.password(), data.username());
    }

    @Override
    public Mono<ResponseEntity<ResponseLogin>> login(@Valid RequestLogin data){
        return authService.login(data.email(), data.password());
    }

    @Override
    public Mono<ResponseEntity<ResponseLogin>> refresh(String cookie){
        return authService.refresh(cookie);
    }

    @Override
    public Mono<ResponseEntity<ResponseLogout>> logout(SecurityUserDetails auth){
        return authService.logout(auth);
    }
}
