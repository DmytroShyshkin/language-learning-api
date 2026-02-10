package com.dmytro.language_learning_api.mapper;

import com.dmytro.language_learning_api.dto.TranslationDTO;
import com.dmytro.language_learning_api.model.Translation;

public class TranslationMapper {
    public TranslationDTO toDto(Translation translation) {
        return new TranslationDTO(
                translation.getId(),
                translation.getTargetLanguage(),
                translation.getText()
                //translation.getWord(),
                //translation.getSource()
        );
    }

    public Translation fromDto(TranslationDTO translationDTO) {
        Translation translation = new Translation();
        translation.setId(translationDTO.id());
        translation.setTargetLanguage(translationDTO.targetLanguage());
        translation.setText(translationDTO.text());

        return translation;
    }
}
