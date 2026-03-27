package com.dmytro.language_learning_api.dto.authentication;

import java.util.UUID;

public record AuthResponse(
        UUID id,
        String accessToken,
        String refreshToken,
        String email,
        String username
) {
}
