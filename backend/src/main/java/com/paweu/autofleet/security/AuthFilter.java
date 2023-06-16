package com.paweu.autofleet.security;

import com.paweu.autofleet.data.repository.UserRepository;
import com.paweu.autofleet.service.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter implements WebFilter {
    private final JwtService jwtService;

    private final UserRepository userRepository;

    public AuthFilter(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        try {
            String email = jwtService.validateAccess(getJwtToken(exchange));
            Mono<SecurityUserDetails> user = userRepository.findByEmail(email).mapNotNull(SecurityUserDetails::new);

            return user.flatMap(u -> {
                var auth = new UsernamePasswordAuthenticationToken(u, u.getUsername(), u.getAuthorities());

                return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
            });
        } catch (Exception e) {
            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.clearContext());
        }
    }

    private String getJwtToken(ServerWebExchange exchange){
        return exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION).substring(7);
    }
}
