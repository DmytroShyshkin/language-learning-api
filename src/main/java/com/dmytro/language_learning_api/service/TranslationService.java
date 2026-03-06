package com.dmytro.language_learning_api.service;

import com.dmytro.language_learning_api.dto.TranslationDTO;
import com.dmytro.language_learning_api.dto.response.TranslationRespons;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

public interface TranslationService {
    //List<TranslationDTO> getTranslationsByWordId(@PathVariable UUID wordId, int pageNo, int pageSize);
    TranslationRespons getTranslationsByWordId(@PathVariable UUID wordId, int pageNo, int pageSize);
    TranslationDTO addTranslation(UUID wordId, TranslationDTO translationDto);
    TranslationDTO getTranslationById(UUID translationId);
    TranslationDTO updateTranslation(UUID translationId, TranslationDTO translationDto);
    void deleteTranslation(UUID translationId);
}
