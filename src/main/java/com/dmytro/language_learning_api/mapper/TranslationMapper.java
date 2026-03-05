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

/*
public class TranslationMapper {
    private final SynonymMapper synonymMapper = new SynonymMapper();

    public TranslationDTO toDto(Translation translation) {
        return new TranslationDTO(
                translation.getId(),
                translation.getTargetLanguage(),
                translation.getTranslatedWord(),
                translation.getDescription(),
                synonymMapper.toDto(translation.getSynonyms())
        );
    }

    public Translation fromDto(TranslationDTO dto) {
        Translation translation = new Translation();
        translation.setId(dto.id());
        translation.setTargetLanguage(dto.targetLanguage());
        translation.setTranslatedWord(dto.translatedWord());
        translation.setDescription(dto.description());

        List<Synonym> synonyms = synonymMapper.fromDtoList(dto.synonyms());

        synonyms.forEach(s -> s.setTranslation(translation));

        translation.setSynonyms(synonyms);

        return translation;
    }

    public List<TranslationDTO> toDto(List<Translation> translations) {
        return translations.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Translation> fromDto(List<TranslationDTO> dtos) {
        return dtos.stream()
                .map(this::fromDto)
                .collect(Collectors.toList());
    }
}
*/