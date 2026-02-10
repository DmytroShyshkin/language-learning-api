package com.dmytro.language_learning_api.exception;

public class WordNotFoundException extends NotFoundException {
    public WordNotFoundException(String message) {
        super(message);
    }
}
