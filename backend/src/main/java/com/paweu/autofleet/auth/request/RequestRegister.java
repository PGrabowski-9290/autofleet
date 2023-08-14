package com.paweu.autofleet.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RequestRegister(
        @NotNull(message = "Email is required") @NotEmpty(message = "Email is required") @Email(message = "Invalid email format", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        String email,
        @NotNull(message = "Username is required") @NotEmpty(message = "Username is required")
        String username,
        @NotNull(message = "Password is required") @NotEmpty(message = "Password is required") @Pattern(message = "Password not meet the requirements", regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d!@#$%^&*]{8,}$")
        String password) {
}
