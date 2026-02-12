package com.dmytro.language_learning_api.service;

import com.dmytro.language_learning_api.dto.TranslationDTO;

import java.util.UUID;

public interface TranslationService {
    TranslationDTO addTranslation(UUID wordId, TranslationDTO translationDto);
    TranslationDTO getTranslationById(UUID translationId);
    TranslationDTO updateTranslation(UUID translationId, TranslationDTO translationDto);
    void deleteTranslation(UUID translationId);
}
