package com.dmytro.language_learning_api.repository;

import com.dmytro.language_learning_api.dto.WordsDTO;
import com.dmytro.language_learning_api.model.Words;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WordsRepository extends JpaRepository<Words, UUID> {
    List<Words> findByOwnerId(UUID ownerId);

}
