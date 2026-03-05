package com.dmytro.language_learning_api.repository;

import com.dmytro.language_learning_api.model.Words;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WordsRepository extends JpaRepository<Words, UUID> {
    /*
    @Query("""
       SELECT w
       FROM Words w
       LEFT JOIN FETCH w.synonyms
       WHERE w.owner.id = :ownerId
       """)
    List<Words> findByOwnerId(UUID ownerId);
    */
    @Query("""
       SELECT w
       FROM Words w
       LEFT JOIN FETCH w.synonyms
       WHERE w.owner.id = :ownerId
       """)
    Page<Words> findByOwnerId(UUID ownerId, Pageable pageable);
}
