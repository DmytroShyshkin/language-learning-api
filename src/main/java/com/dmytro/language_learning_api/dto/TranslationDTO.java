package com.dmytro.language_learning_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.UUID;

public record TranslationDTO(
        UUID id,
        @NotBlank(message = "Target language must not be blank")
        @Pattern(
                regexp = "^[a-z]{2}(-[A-Z]{2})?$",
                message = "Language must be ISO format like 'en' or 'en-US'"
        )
        String targetLanguage,
        @NotBlank(message = "Translated word must not be blank")
        @Size(min = 1, max = 200, message = "Translation must be between 1 and 200 characters")
        String translatedWord,

        String description,

        List<SynonymDTO> synonyms
        //@NotBlank
        //Words word,
        //String source
        ) {
}
