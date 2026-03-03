package com.dmytro.language_learning_api.service;

import com.dmytro.language_learning_api.dto.TranslationDTO;
import com.dmytro.language_learning_api.exception.NotFoundException.TranslationNotFoundException;
import com.dmytro.language_learning_api.exception.NotFoundException.WordNotFoundException;
import com.dmytro.language_learning_api.mapper.TranslationMapper;
import com.dmytro.language_learning_api.model.Translation;
import com.dmytro.language_learning_api.model.Words;
import com.dmytro.language_learning_api.repository.TranslationRepository;
import com.dmytro.language_learning_api.repository.WordsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TranslationServiceImpl implements TranslationService {

    private final TranslationRepository translationRepository;
    private final TranslationMapper translationMapper;
    private final WordsRepository wordsRepository;

    @Override
    public List<TranslationDTO> getTranslationsByWordId(UUID wordId) {
        List<Translation> translations =
                translationRepository.findByWordId(wordId);

        if (translations.isEmpty()) {
            throw new TranslationNotFoundException("Translations not found for word id: " + wordId);
        }

        return translationMapper.toDto(translations);
    }

    @Override
    public TranslationDTO addTranslation(UUID wordId, TranslationDTO translationDto) {
        Words word = wordsRepository.findById(wordId)
                .orElseThrow(() -> new WordNotFoundException("Word not found"));

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

        translation.setTranslatedWord(translationDto.translatedWord());
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
