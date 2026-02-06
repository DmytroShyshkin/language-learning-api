package com.dmytro.language_learning_api.dto;

import com.dmytro.language_learning_api.model.Words;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record TranslationDTO(
        UUID id,
        @NotBlank
        @Pattern(regexp = "^[a-z]{2}(-[A-Z]{2})?$")
        String targetLanguage,
        @NotBlank
        @Size(min = 1, max = 200)
        String text,
        @NotBlank
        Words word,
        String source) {
}
