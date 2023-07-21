package com.paweu.autofleet.auth.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RequestLogin(
        @NotNull(message = "Email is required") @NotEmpty(message = "Brand is required")
        String email,
        @NotNull(message = "Password is required") @NotEmpty(message = "Password is required")
        String password) {
}
