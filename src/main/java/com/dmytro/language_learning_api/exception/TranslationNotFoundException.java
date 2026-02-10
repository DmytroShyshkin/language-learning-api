package com.dmytro.language_learning_api.exception;

public class TranslationNotFoundException extends NotFoundException {
    public TranslationNotFoundException(String message) {
        super(message);
    }
}
