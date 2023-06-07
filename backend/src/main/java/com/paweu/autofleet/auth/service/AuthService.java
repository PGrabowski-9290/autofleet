package com.paweu.autofleet.auth.service;

import com.paweu.autofleet.auth.response.LoginResponse;
import com.paweu.autofleet.data.repository.UserRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    public Mono<ResponseEntity<LoginResponse>> login(@NonNull String email, @NonNull String password){
        return userRepository.findByEmail(email)
                .map(user -> {
                    if (!passwordEncoder.matches(password, user.getPassword())) {
                        return Mono.error(new Exception("Password dont match"));
                    }
                    return
                });
    }
}
