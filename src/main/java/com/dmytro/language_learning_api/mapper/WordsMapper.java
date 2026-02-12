package com.dmytro.language_learning_api.mapper;

import com.dmytro.language_learning_api.dto.WordsDTO;
import com.dmytro.language_learning_api.model.Words;

import java.util.List;
import java.util.stream.Collectors;

public class WordsMapper {
    // --- Single word ---
    public WordsDTO toDto(Words word){
        return new WordsDTO(
                word.getId(),
                word.getSourceLanguage(),
                word.getOriginalWord(),
                word.getOwner().getId()
                );
    }

    public Words fromDto(WordsDTO dto){
        Words word = new Words();
        word.setId(dto.id());
        word.setSourceLanguage(dto.sourceLanguage());
        word.setOriginalWord(dto.originalWord());
        //word.setOwner(dto.owner());

        return word;
    }

    // --- List of words ---
    public List<WordsDTO> toDto(List<Words> words) {
        return words.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Words> fromDto(List<WordsDTO> dtos) {
        return dtos.stream()
                .map(this::fromDto)
                .collect(Collectors.toList());
    }
}
