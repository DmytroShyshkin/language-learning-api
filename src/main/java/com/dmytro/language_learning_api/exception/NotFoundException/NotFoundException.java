package com.dmytro.language_learning_api.exception.NotFoundException;

public abstract class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
