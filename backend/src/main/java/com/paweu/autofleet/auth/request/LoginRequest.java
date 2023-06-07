package com.paweu.autofleet.auth.request;

public record LoginRequest(String email,
                           String password) {
}
