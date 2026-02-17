package com.dmytro.language_learning_api.service;

import com.dmytro.language_learning_api.dto.TranslationDTO;
import com.dmytro.language_learning_api.dto.WordsDTO;
import com.dmytro.language_learning_api.mapper.TranslationMapper;
import com.dmytro.language_learning_api.mapper.WordsMapper;
import com.dmytro.language_learning_api.model.Translation;
import com.dmytro.language_learning_api.model.Users;
import com.dmytro.language_learning_api.model.Words;
import com.dmytro.language_learning_api.repository.UsersRepository;
import com.dmytro.language_learning_api.repository.WordsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WordsServiceTest {

    @Mock
    private WordsMapper wordsMapper;
    @Mock
    TranslationMapper translationMapper;
    @Mock
    private WordsRepository wordsRepository;
    @Mock
    private UsersRepository usersRepository;
    @InjectMocks
    private  WordsServiceImpl wordsService;

    UUID id = UUID.randomUUID();

    @Test
    void shouldReturnCreatedWord(){
        UUID ownerId = UUID.randomUUID();
        WordsDTO wordDto = new WordsDTO(
                null,
                "EN",
                "orWord",
                ownerId
        );

        Users owner = new Users();
        owner.setId(ownerId);

        Words word = new Words();
        word.setSourceLanguage("EN");
        word.setOriginalWord("orWord");
        word.setOwner(owner);

        Words savedWord = new Words();
        savedWord.setId(UUID.randomUUID());
        savedWord.setSourceLanguage("EN");
        savedWord.setOriginalWord("orWord");
        savedWord.setOwner(owner);

        when(wordsMapper.fromDto(wordDto))
                .thenReturn(word);

        when(wordsRepository.save(word))
                .thenReturn(savedWord);

        when(usersRepository.findById(ownerId))
                .thenReturn(Optional.of(owner));

        when(wordsMapper.toDto(savedWord))
                .thenReturn(new WordsDTO(
                        savedWord.getId(),
                        savedWord.getSourceLanguage(),
                        savedWord.getOriginalWord(),
                        ownerId
                ));

        WordsDTO result = wordsService.createWord(wordDto);

        assertEquals("EN", result.sourceLanguage());

        verify(wordsRepository).save(word);
    }

    @Test
    void shouldReturnWordWhenWordExists() {
        UUID id = UUID.randomUUID();

        Words word = new Words();
        word.setId(id);
        word.setSourceLanguage("EN");
        word.setOriginalWord("orWord");

        // No se puede usar toDto() aquí, ya que Mockito se quejará.
        // WordsDTO wordDto = wordsMapper.toDto(word);
        WordsDTO wordDto = new WordsDTO(word.getId(), word.getSourceLanguage(), word.getOriginalWord(), null);

        when(wordsRepository.findById(id))
                .thenReturn(Optional.of(word));

        when(wordsMapper.toDto(word))
                .thenReturn(wordDto);

        // Llamada de servicio
        WordsDTO result = wordsService.getWordById(id);
        // Verificación del resultado
        assertEquals(wordDto, result);
        // Comprobamos las llamadas reales
        verify(wordsRepository).findById(id);
        verify(wordsMapper).toDto(word);
    }

    @Test
    void shouldReturnUpdatedSourceLanguage() {
        UUID id = UUID.randomUUID();
        String newSourceLanguage = "UA";

        Words word = new Words();
        word.setId(id);
        word.setSourceLanguage("EN");
        word.setOriginalWord("orWord");

        WordsDTO updatedDto =
                new WordsDTO(id, newSourceLanguage, "orWord", null);

        // mock find
        when(wordsRepository.findById(id))
                .thenReturn(Optional.of(word));

        // mock save
        when(wordsRepository.save(any(Words.class)))
                .thenReturn(word);

        // mock mapper
        when(wordsMapper.toDto(word))
                .thenReturn(updatedDto);

        // Llamada de servicio
        WordsDTO result =
                wordsService.updateSourceLanguage(id, newSourceLanguage);

        // Verificación del resultado
        assertEquals(newSourceLanguage, result.sourceLanguage());

        // Comprobamos las llamadas reales
        verify(wordsRepository).findById(id);
        verify(wordsRepository).save(word);
        verify(wordsMapper).toDto(word);
    }

    @Test
    void shouldReturnUpdatedOriginalWord() {
        UUID id = UUID.randomUUID();
        String newOriginalWord = "newWord";

        Words word = new Words();
        word.setId(id);
        word.setSourceLanguage("EN");
        word.setOriginalWord("orWord");

        WordsDTO updatedDto =
                new WordsDTO(id, "EN", newOriginalWord, null);

        // mock find
        when(wordsRepository.findById(id))
                .thenReturn(Optional.of(word));

        // mock save
        when(wordsRepository.save(any(Words.class)))
                .thenReturn(word);

        // mock mapper
        when(wordsMapper.toDto(word))
                .thenReturn(updatedDto);

        // Llamada de servicio
        WordsDTO result =
                wordsService.updateOriginalWord(id, newOriginalWord);

        // Verificación del resultado
        assertEquals(newOriginalWord, result.originalWord());

        // Comprobamos las llamadas reales
        verify(wordsRepository).findById(id);
        verify(wordsRepository).save(word);
        verify(wordsMapper).toDto(word);
    }

    @Test
    void shouldReturnAllWordsByOwnerId() {
        UUID ownerId = UUID.randomUUID();

        Words word1 = new Words();
        word1.setId(UUID.randomUUID());
        word1.setOriginalWord("hello");

        Words word2 = new Words();
        word2.setId(UUID.randomUUID());
        word2.setOriginalWord("world");

        List<Words> wordsList = List.of(word1, word2);

        WordsDTO dto1 = new WordsDTO(word1.getId(), null, "hello", null);
        WordsDTO dto2 = new WordsDTO(word2.getId(), null, "world", null);

        when(wordsRepository.findByOwnerId(ownerId))
                .thenReturn(wordsList);

        when(wordsMapper.toDto(word1)).thenReturn(dto1);
        when(wordsMapper.toDto(word2)).thenReturn(dto2);

        List<WordsDTO> result =
                wordsService.getAllWordsByOwnerId(ownerId);

        assertEquals(2, result.size());
        assertEquals("hello", result.get(0).originalWord());
        assertEquals("world", result.get(1).originalWord());

        verify(wordsRepository).findByOwnerId(ownerId);
        verify(wordsMapper).toDto(word1);
        verify(wordsMapper).toDto(word2);
    }

    @Test
    void shouldReturnAddedTranslationToWord() {
        UUID id = UUID.randomUUID();

        Words word = new Words();
        word.setId(id);
        word.setSourceLanguage("EN");
        word.setOriginalWord("orWord");
        word.setTranslations(new ArrayList<>());

        Translation translation = new Translation();
        translation.setId(UUID.randomUUID());
        translation.setTargetLanguage("UA");
        translation.setText("text");

        TranslationDTO translationDto = new TranslationDTO(
                translation.getId(),
                translation.getTargetLanguage(),
                translation.getText()
        );

        when(wordsRepository.findById(id))
                .thenReturn(Optional.of(word));

        when(translationMapper.fromDto(translationDto))
                .thenReturn(translation);

        when(wordsRepository.save(any(Words.class)))
                .thenReturn(word);

        when(wordsMapper.toDto(word))
                .thenReturn(new WordsDTO(id, "EN", "orWord", null));

        WordsDTO result =
                wordsService.addTranslationToWord(id, translationDto);

        assertEquals(1, word.getTranslations().size());

        verify(wordsRepository).findById(id);
        verify(translationMapper).fromDto(translationDto);
        verify(wordsRepository).save(word);
        verify(wordsMapper).toDto(word);
    }

    @Test
    void shouldDeleteWordWhenWordExists() {
        UUID id = UUID.randomUUID();

        Words word = new Words();
        word.setId(id);

        when(wordsRepository.findById(id))
                .thenReturn(Optional.of(word));

        wordsService.deleteWord(id);

        verify(wordsRepository).findById(id);
        verify(wordsRepository).delete(word);
    }

    @Test
    void shouldThrowExceptionWhenWordNotFound() {
        UUID id = UUID.randomUUID();

        when(wordsRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                wordsService.deleteWord(id)
        );

        verify(wordsRepository).findById(id);
        verify(wordsRepository, never()).delete(any());
    }

}
