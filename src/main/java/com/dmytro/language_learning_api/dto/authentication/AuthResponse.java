package com.dmytro.language_learning_api.dto.authentication;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        String email,
        String username
) {
}
