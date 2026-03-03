package com.dmytro.language_learning_api.dto;

public record UpdateWordRequest(
        String sourceLanguage,
        String originalWord
) {
}
