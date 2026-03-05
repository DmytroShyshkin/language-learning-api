package com.dmytro.language_learning_api.mapper;

import com.dmytro.language_learning_api.dto.WordsDTO;
import com.dmytro.language_learning_api.model.Words;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface WordsMapper {

    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(target = "synonymIds", ignore = true)
    WordsDTO toDto(Words word);

    default Set<UUID> map(Set<Words> synonyms) {
        if (synonyms == null) return Collections.emptySet();

        return synonyms.stream()
                .map(Words::getId)
                .collect(Collectors.toSet());
    }

    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "translations", ignore = true)
    @Mapping(target = "synonyms", ignore = true)
    Words fromDto(WordsDTO dto);

    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(target = "synonymIds", ignore = true)
    List<WordsDTO> toDto(List<Words> words);
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "translations", ignore = true)
    @Mapping(target = "synonyms", ignore = true)
    List<Words> fromDto(List<WordsDTO> dtos);
}
/*
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
*/