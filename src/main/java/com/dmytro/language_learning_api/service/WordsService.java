package com.dmytro.language_learning_api.service;

import com.dmytro.language_learning_api.dto.CreateWordRequestDTO;
import com.dmytro.language_learning_api.dto.TranslationDTO;
import com.dmytro.language_learning_api.dto.UpdateWordRequest;
import com.dmytro.language_learning_api.dto.WordsDTO;
import com.dmytro.language_learning_api.dto.response.PageResponse;

import java.util.UUID;

public interface WordsService {

    WordsDTO createWord(CreateWordRequestDTO request);
    WordsDTO getWordById(UUID wordId);
    //List<WordsDTO> getWordsByUser(UUID ownerId);
    WordsDTO updateWord(UUID wordId, UpdateWordRequest updateWordRequest);
    PageResponse<WordsDTO> getAllWordsByOwnerId(UUID wordId, int pageNo, int pageSize);
    WordsDTO addTranslationToWord(UUID wordId, TranslationDTO dto);
    void addSynonym(UUID wordId, UUID synonymId);
    void deleteWord(UUID wordId);
}
