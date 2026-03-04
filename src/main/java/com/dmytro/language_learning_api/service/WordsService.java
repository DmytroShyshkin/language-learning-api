package com.dmytro.language_learning_api.service;

import com.dmytro.language_learning_api.dto.TranslationDTO;
import com.dmytro.language_learning_api.dto.UpdateWordRequest;
import com.dmytro.language_learning_api.dto.WordsDTO;

import java.util.List;
import java.util.UUID;

public interface WordsService {

    WordsDTO createWord(WordsDTO dto);
    WordsDTO getWordById(UUID wordId);
    //List<WordsDTO> getWordsByUser(UUID ownerId);
    WordsDTO updateWord(UUID wordId, UpdateWordRequest updateWordRequest);
    List<WordsDTO> getAllWordsByOwnerId(UUID wordId);
    WordsDTO addTranslationToWord(UUID wordId, TranslationDTO dto);
    void addSynonym(UUID wordId, UUID synonymId);
    void deleteWord(UUID wordId);
}
