package com.dmytro.language_learning_api.mapper;

import com.dmytro.language_learning_api.dto.SynonymDTO;
import com.dmytro.language_learning_api.model.Synonym;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SynonymMapper {

    SynonymDTO toDto(Synonym synonym);

    Synonym fromDto(SynonymDTO dto);
/*
public class SynonymMapper {

    public SynonymDTO toDto(Synonym synonym) {
        return new SynonymDTO(
                synonym.getId(),
                synonym.getSynonymText()
        );
    }

    public Synonym fromDto(SynonymDTO dto) {
        Synonym synonym = new Synonym();
        synonym.setId(dto.id());
        synonym.setSynonymText(dto.synonymText());
        return synonym;
    }

    public List<SynonymDTO> toDto(List<Synonym> synonyms) {
        return synonyms.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Synonym> fromDtoList(List<SynonymDTO> dtos) {
        return dtos.stream()
                .map(this::fromDto)
                .collect(Collectors.toList());
    }
    */
}
