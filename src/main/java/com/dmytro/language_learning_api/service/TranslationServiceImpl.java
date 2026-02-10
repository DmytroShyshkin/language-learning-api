package com.dmytro.language_learning_api.service;

import com.dmytro.language_learning_api.dto.TranslationDTO;
import com.dmytro.language_learning_api.exception.TranslationNotFoundException;
import com.dmytro.language_learning_api.mapper.TranslationMapper;
import com.dmytro.language_learning_api.model.Translation;
import com.dmytro.language_learning_api.model.Words;
import com.dmytro.language_learning_api.repository.TranslationRepository;
import com.dmytro.language_learning_api.repository.WordsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService {

    final TranslationRepository translationRepository;
    final TranslationMapper translationMapper;
    final WordsRepository wordsRepository;

    @Override
    public TranslationDTO addTranslation(UUID wordId, TranslationDTO translationDto) {
        Words word = wordsRepository.findById(wordId)
                .orElseThrow(() -> new RuntimeException("Word not found"));

        Translation translation = translationMapper.fromDto(translationDto);
        translation.setWord(word);

        translationRepository.save(translation);

        return translationMapper.toDto(translation);
    }

    @Override
    public TranslationDTO getTranslationById(UUID translationId) {
        Translation translation = getTranslationOrThrow(translationId);
        return translationMapper.toDto(translation);
    }

    @Override
    public TranslationDTO updateTranslation(UUID translationId, TranslationDTO translationDto) {
        Translation translation = getTranslationOrThrow(translationId);

        translation.setText(translationDto.text());
        translation.setTargetLanguage(translationDto.targetLanguage());

        translationRepository.save(translation);

        return translationMapper.toDto(translation);
    }

    @Override
    public void deleteTranslation(UUID translationId) {
        Translation translation = getTranslationOrThrow(translationId);

        translationRepository.delete(translation);
    }

    // Clases auxiliares
    private Translation getTranslationOrThrow(UUID translationId) {
        return translationRepository.findById(translationId)
                .orElseThrow(() -> new TranslationNotFoundException("Translationwith id " + translationId + " not found"));
    }
}
