package com.dmytro.language_learning_api.service;

import com.dmytro.language_learning_api.dto.SynonymDTO;
import com.dmytro.language_learning_api.dto.TranslationDTO;
import com.dmytro.language_learning_api.mapper.TranslationMapper;
import com.dmytro.language_learning_api.model.Translation;
import com.dmytro.language_learning_api.model.Words;
import com.dmytro.language_learning_api.repository.TranslationRepository;
import com.dmytro.language_learning_api.repository.WordsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TranslationServiceTest {
    @Mock
    private TranslationRepository translationRepository;
    @Mock
    private TranslationMapper translationMapper;
    @Mock
    private WordsRepository wordsRepository;
    @InjectMocks
    private TranslationServiceImpl translationService;

    @Test
    void shouldReturnWordWithAddedTranslation() {
        UUID id = UUID.randomUUID();

        Words word = new Words();
        word.setId(id);
        word.setSourceLanguage("EN");
        word.setOriginalWord("orWord");

        when(wordsRepository.findById(id))
                .thenReturn(Optional.of(word));

        List<SynonymDTO> synonyms = List.of();

        TranslationDTO translationDTO = new TranslationDTO(
                UUID.randomUUID(),
                "EN",
                "text",
                "syn",
                synonyms
        );

        Translation translation = new Translation();
        translation.setId(translationDTO.id());
        translation.setTargetLanguage("EN");
        translation.setTranslatedWord("text");

        when(translationMapper.fromDto(translationDTO))
                .thenReturn(translation);

        // El servicio llamará a translation.setWord(word)
        when(translationRepository.save(any(Translation.class)))
                .thenReturn(translation);

        TranslationDTO newTranslationDTO = new TranslationDTO(
                translation.getId(),
                translation.getTargetLanguage(),
                translation.getTranslatedWord(),
                translation.getDescription(),
                synonyms
        );

        when(translationMapper.toDto(translation))
                .thenReturn(newTranslationDTO);

        TranslationDTO result =
                translationService.addTranslation(id, translationDTO);

        // assertions
        assertEquals(newTranslationDTO, result);

        verify(wordsRepository).findById(id);
        verify(translationMapper).fromDto(translationDTO);
        verify(translationRepository).save(translation);
        verify(translationMapper).toDto(translation);
    }

    @Test
    void shouldReturnTranslationById() {
        UUID id = UUID.randomUUID();

        Translation translation = new Translation();

        when(translationRepository.findById(id))
                .thenReturn(Optional.of(translation));

        List<SynonymDTO> synonyms = List.of();

        TranslationDTO translationDTO = new TranslationDTO(
                translation.getId(),
                translation.getTargetLanguage(),
                translation.getTranslatedWord(),
                translation.getDescription(),
                synonyms
        );

        when(translationMapper.toDto(translation))
                .thenReturn(translationDTO);

        TranslationDTO result = translationService.getTranslationById(id);

        assertEquals(translationDTO, result);

        verify(translationRepository).findById(id);
        verify(translationMapper).toDto(translation);
    }

    @Test
    void shouldReturnUpdatedTranslation() {
        UUID id = UUID.randomUUID();
        Translation translation = new Translation();
        translation.setId(id);

        when(translationRepository.findById(id))
                .thenReturn(Optional.of(translation));

        List<SynonymDTO> synonyms = List.of();

        TranslationDTO translationDTO = new TranslationDTO(
                UUID.randomUUID(),
                "EN",
                "text",
                "syn",
                synonyms
        );

        translation.setTargetLanguage(translationDTO.targetLanguage());
        translation.setTranslatedWord(translationDTO.translatedWord());

        Translation savedTranslation = new Translation();

        when(translationRepository.save(any(Translation.class)))
                .thenReturn(savedTranslation);

        when(translationMapper.toDto(translation))
                .thenReturn(translationDTO);

        TranslationDTO result =
                translationService.updateTranslation(id, translationDTO);

        assertEquals("EN", translation.getTargetLanguage());
        assertEquals("text", translation.getTranslatedWord());
        assertEquals(translationDTO, result);

        verify(translationRepository).findById(id);
        verify(translationRepository).save(translation);
        verify(translationMapper).toDto(translation);

    }

    @Test
    void shouldDeleteTranslationWhenTranslationExists() {
        UUID id = UUID.randomUUID();
        Translation translation = new Translation();
        translation.setId(id);

        when(translationRepository.findById(id))
                .thenReturn(Optional.of(translation));

        translationService.deleteTranslation(id);

        verify(translationRepository).findById(id);
        verify(translationRepository).delete(translation);
    }

    @Test
    void shouldThrowExceptionWhenTranslationNotFound() {
        UUID id = UUID.randomUUID();

        when(translationRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                translationService.deleteTranslation(id));

        verify(translationRepository).findById(id);
        verify(translationRepository, never()).delete(any());
    }
}
