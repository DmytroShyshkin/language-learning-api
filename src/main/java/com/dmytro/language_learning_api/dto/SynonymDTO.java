package com.dmytro.language_learning_api.dto;

import java.util.UUID;

public record SynonymDTO(
        UUID id,
        String synonymText
) {
}
