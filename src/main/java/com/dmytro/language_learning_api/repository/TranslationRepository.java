package com.dmytro.language_learning_api.repository;

import com.dmytro.language_learning_api.model.Translation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TranslationRepository extends JpaRepository<Translation, UUID> {
    List<Translation> findByWordId(UUID wordId);
    List<Translation> findByTargetLanguageAndWordOwnerId(String targetLanguage, UUID ownerId);
}
