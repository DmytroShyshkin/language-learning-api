package com.dmytro.language_learning_api.dto.error;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ValidationErrorResponse extends ErrorResponse{

    private List<FieldValidationError> errors;

    public ValidationErrorResponse(
            LocalDateTime timestamp,
            int status,
            String error,
            String message,
            String path,
            List<FieldValidationError> errors
    ) {
        super(timestamp, status, error, message, path);
        this.errors = errors;
    }

}
