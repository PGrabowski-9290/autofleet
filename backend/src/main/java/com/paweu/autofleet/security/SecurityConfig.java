package com.paweu.autofleet.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    protected SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        return http
                .authorizeExchange(ex -> {
                    ex.pathMatchers(HttpMethod.POST, "/auth/login", "/auth/register").permitAll();
                    ex.pathMatchers(HttpMethod.GET, "/auth/refresh").permitAll();
                    ex.anyExchange().authenticated();
                })
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
    }
}
