package com.dmytro.language_learning_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record UsersDTO(
        UUID id,
        @NotBlank(message = "Email must be valid")
        @Email(message = "Email must be valid")
        String email,
        @NotBlank(message = "Password must be valid")
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,
        @NotBlank(message = "Username must be valid")
        @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
        String username) {
}
