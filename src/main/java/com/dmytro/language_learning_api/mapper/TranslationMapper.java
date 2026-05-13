package com.dmytro.language_learning_api.mapper;

import com.dmytro.language_learning_api.dto.TranslationDTO;
import com.dmytro.language_learning_api.model.Translation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TranslationMapper {

    TranslationDTO toDto(Translation translation);

    Translation fromDto(TranslationDTO translationDTO);

    List<TranslationDTO> toDto(List<Translation> translation);

    List<Translation> fromDto(List<TranslationDTO> translationDTO);
}