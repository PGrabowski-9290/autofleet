package com.paweu.autofleet.auth.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RequestRegister(
        @NotNull(message = "Email is required") @NotEmpty(message = "Email is required")
        String email,
        @NotNull(message = "Username is required") @NotEmpty(message = "Username is required")
        String username,
        @NotNull(message = "Password is required") @NotEmpty(message = "Password is required")
        String password) {
}
