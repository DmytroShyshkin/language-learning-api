package com.dmytro.language_learning_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.dmytro.language_learning_api.model.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, UUID> {
    Page<Translation> findByWordId(UUID wordId, Pageable pageable);
    List<Translation> findByTargetLanguageAndWordOwnerId(String targetLanguage, UUID ownerId);
}
