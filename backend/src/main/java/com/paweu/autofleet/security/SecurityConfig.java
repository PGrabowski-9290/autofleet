package com.paweu.autofleet.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final String[] AUTH_WHITELIST_SWAGGER = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**"
    };

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, AuthFilter authFilter){
        return http
                .authorizeExchange(ex -> {
                    ex.pathMatchers(HttpMethod.POST, "/auth/login", "/auth/register").permitAll();
                    ex.pathMatchers(HttpMethod.GET, "/auth/refresh").permitAll();
                    ex.pathMatchers(AUTH_WHITELIST_SWAGGER).permitAll();
                    ex.anyExchange().authenticated();
                })
                .addFilterAt(authFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .exceptionHandling(exceptionHandlingSpec -> {
                     exceptionHandlingSpec.authenticationEntryPoint((exchange, ex) -> {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return Mono.empty();
                    });
                    exceptionHandlingSpec.accessDeniedHandler((exchange, denied) -> {
                        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                        return Mono.empty();
                    });
                }
                )
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
    }


}
