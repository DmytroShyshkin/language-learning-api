package com.dmytro.language_learning_api.service;

import com.dmytro.language_learning_api.dto.WordsDTO;
import com.dmytro.language_learning_api.mapper.WordsMapper;
import com.dmytro.language_learning_api.model.Users;
import com.dmytro.language_learning_api.model.Words;
import com.dmytro.language_learning_api.repository.UsersRepository;
import com.dmytro.language_learning_api.repository.WordsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WordsServiceTest {

    @Mock
    private WordsMapper wordsMapper;
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

}
