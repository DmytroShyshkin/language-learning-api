package com.dmytro.language_learning_api.service;

import com.dmytro.language_learning_api.dto.TranslationDTO;
import com.dmytro.language_learning_api.dto.WordsDTO;

import java.util.List;
import java.util.UUID;

public interface WordsService {

    WordsDTO createWord(WordsDTO dto);
    WordsDTO getWordById(UUID wordId);
    //List<WordsDTO> getWordsByUser(UUID ownerId);
    WordsDTO updateSourceLanguage(UUID wordId, String newSourceWord);
    WordsDTO updateOriginalWord(UUID wordId, String newOriginalWord);
    List<WordsDTO> getAllWordsByOwnerId(UUID wordId);
    public WordsDTO addTranslationToWord(UUID wordId, TranslationDTO dto);
    void deleteWord(UUID wordId);
}
