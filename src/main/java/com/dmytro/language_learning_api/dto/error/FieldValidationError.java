package com.dmytro.language_learning_api.dto.error;

public record FieldValidationError(
        String field,
        String message
) {
}
