package com.paweu.autofleet.auth;

import com.paweu.autofleet.auth.request.RequestLogin;
import com.paweu.autofleet.auth.request.RequestRegister;
import com.paweu.autofleet.auth.response.ResponseLogin;
import com.paweu.autofleet.auth.response.ResponseLogout;
import com.paweu.autofleet.auth.response.ResponseRegister;
import com.paweu.autofleet.security.SecurityUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication")
public interface AuthController {

    @Operation(
            description = "Register new user in system and send access token and refresh token cookie",
            summary = "Register new user",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            useReturnTypeSchema = true
                    )
            }
    )
    @PostMapping("/register")
    Mono<ResponseEntity<ResponseRegister>> register(@Valid @RequestBody RequestRegister data);

    @Operation(
            description = "Login user into system and send access token and refresh token cookie",
            summary = "Login user",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200",
                            useReturnTypeSchema = true
                    ),
                    @ApiResponse(
                            description = "Unauthenticated",
                            responseCode = "401",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            }
    )
    @PostMapping("/login")
    Mono<ResponseEntity<ResponseLogin>> login(@RequestBody RequestLogin data);


    @Operation(
            description = "Generate new login token and refresh token if refresh token is not expired",
            summary = "Refresh login token",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "401",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            }
    )
    @GetMapping("/refresh")
    Mono<ResponseEntity<ResponseLogin>> refresh(@CookieValue(name = "jwt") String cookie);

    @Operation(
            description = "Logout user and deactivate valid refresh tokens",
            summary = "Logout user"
    )
    @SecurityRequirement(name = "bearer-key")
    @GetMapping("/logout")
    Mono<ResponseEntity<ResponseLogout>> logout(@AuthenticationPrincipal SecurityUserDetails auth);
}