package com.paweu.autofleet.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RequestLogin(
        @NotNull(message = "Email is required") @NotEmpty(message = "Brand is required") @Email(message = "Invalid email format")
        String email,
        @NotNull(message = "Password is required") @NotEmpty(message = "Password is required") @Pattern(message = "Password net meet the requirements", regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d!@#$%^&*]{8,}$")
        String password) {
}
