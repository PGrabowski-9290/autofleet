package com.paweu.autofleet.unit.service;

import com.paweu.autofleet.auth.response.ResponseLogin;
import com.paweu.autofleet.auth.response.ResponseRegister;
import com.paweu.autofleet.auth.service.AuthService;
import com.paweu.autofleet.data.models.User;
import com.paweu.autofleet.data.repository.UserRepository;
import com.paweu.autofleet.error.exceptions.UserAlreadyExistException;
import com.paweu.autofleet.error.exceptions.WrongCredentialsException;
import com.paweu.autofleet.service.JwtService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
public class AuthServiceTests {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;
    @Mock
    JwtService jwtService;
    @Mock
    PasswordEncoder passwordEncoder;
    private String USER_EMAIL = "test@email.com";
    private String USER_PASSWORD = "password";
    private String USER_USERNAME = "Test";

    @BeforeEach
    void setup() {
        when(jwtService.generateRefreshToken(anyString())).thenReturn("generatedRefreshToken");
        when(jwtService.generateAccessToken(anyString())).thenReturn("generatedAccessToken");

    }

    @Test
    void shouldRegisterNewUserAndSendRegisterResponse() {
        User user = User.builder()
                .email(USER_EMAIL)
                .password("encodedPassword")
                .username(USER_USERNAME)
                .refToken("generatedRefreshToken")
                .build();
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Mono.empty());

        Mono<ResponseEntity<ResponseRegister>> monoResult = authService.register(USER_EMAIL, USER_PASSWORD, USER_USERNAME);

        StepVerifier
                .create(monoResult)
                .consumeNextWith(resp -> {
                    ResponseRegister responseRegister = resp.getBody();
                    Assertions.assertNotNull(responseRegister);
                    Assertions.assertEquals(responseRegister.message(), "Registered");
                    Assertions.assertNotNull(responseRegister.accessToken());
                })
                .verifyComplete();
    }

    @Test
    void shouldNotRegister_throwUserAlreadyExistException() {
        User user = User.builder()
                .email(USER_EMAIL)
                .password("encodedPassword")
                .username(USER_USERNAME)
                .refToken("generatedRefreshToken")
                .build();
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Mono.just(user));

        Mono<ResponseEntity<ResponseRegister>> monoResult = authService.register(USER_EMAIL, USER_PASSWORD, USER_USERNAME);

        StepVerifier
                .create(monoResult)
                .expectErrorMatches(throwable -> throwable instanceof UserAlreadyExistException)
                .verify();
    }

    @Test
    void shouldLoginUser_sendResponseLogin() {
        User user = User.builder()
                .email(USER_EMAIL)
                .password("encodedPassword")
                .username(USER_USERNAME)
                .refToken("generatedRefreshToken")
                .build();
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Mono.just(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        Mono<ResponseEntity<ResponseLogin>> monoResult = authService.login(USER_EMAIL, USER_PASSWORD);

        StepVerifier
                .create(monoResult)
                .consumeNextWith(resp -> {
                    Assertions.assertNotNull(resp.getBody());
                    Assertions.assertNotNull(resp.getBody().accessToken());
                    Assertions.assertNotNull(resp.getBody().username());
                    Assertions.assertNotNull(resp.getHeaders().get("Set-Cookie").get(0));
                    boolean test = resp.getHeaders().get("Set-Cookie").get(0).startsWith("jwt");
                    Assertions.assertTrue(test);
                })
                .verifyComplete();
    }

    @Test
    void shouldLoginFail_UserNotFound_expectWrongCredentialsException() {
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Mono.empty());
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        Mono<ResponseEntity<ResponseLogin>> monoResult = authService.login(USER_EMAIL, USER_PASSWORD);

        StepVerifier
                .create(monoResult)
                .expectErrorMatches(throwable -> throwable instanceof WrongCredentialsException)
                .verify();
    }

    @Test
    void shouldLoginFail_IncorrectPassword_expectWrongCredentialsException() {
        User user = User.builder()
                .email(USER_EMAIL)
                .password("encodedPassword")
                .username(USER_USERNAME)
                .refToken("generatedRefreshToken")
                .build();
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Mono.just(user));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        Mono<ResponseEntity<ResponseLogin>> monoResult = authService.login(USER_EMAIL, USER_PASSWORD);

        StepVerifier
                .create(monoResult)
                .expectErrorMatches(throwable -> throwable instanceof WrongCredentialsException)
                .verify();
    }
}
