package com.dmytro.language_learning_api.dto;

public record CreateWordRequestDTO(
        String sourceLanguage,
        String originalWord
) {
}
