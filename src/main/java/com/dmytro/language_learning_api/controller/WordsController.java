package com.dmytro.language_learning_api.controller;

import com.dmytro.language_learning_api.dto.TranslationDTO;
import com.dmytro.language_learning_api.dto.WordsDTO;
import com.dmytro.language_learning_api.service.WordsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/words")
@AllArgsConstructor
public class WordsController {

    public final WordsService wordsService;

    @GetMapping("/{wordId}")
    public ResponseEntity<WordsDTO> getWords(@PathVariable UUID wordId) {
        return ResponseEntity.ok(wordsService.getWordById(wordId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WordsDTO>> getAllWordsByOwnerId(UUID userId) {
        return ResponseEntity.ok(wordsService.getAllWordsByOwnerId(userId));
    }

    @PostMapping
    public ResponseEntity<WordsDTO> createWord(@Valid @RequestBody WordsDTO wordsDto) {
        return ResponseEntity.ok(wordsService.createWord(wordsDto));
    }

    @PutMapping("/{wordId}/original-word")
    public ResponseEntity<WordsDTO> updateOriginalWordById(@PathVariable UUID originalWordId, @RequestBody String updatedOriginalWord) {
        return ResponseEntity.ok(wordsService.updateOriginalWord(originalWordId, updatedOriginalWord));
    }

    @PutMapping("/{wordId}/source-language")
    public ResponseEntity<WordsDTO> updateSourceWordById(@PathVariable UUID sourceWordId, @RequestBody String updatedSourceWord) {
        return ResponseEntity.ok(wordsService.updateSourceLanguage(sourceWordId, updatedSourceWord));
    }

    @PutMapping("/{wordId}/add-translation-to-word")
    public ResponseEntity<WordsDTO> addTranslationToWordById(@PathVariable UUID wordId, @Valid @RequestBody TranslationDTO translationDto) {
        return  ResponseEntity.ok(wordsService.addTranslationToWord(wordId, translationDto));
    }

    @DeleteMapping("/{wordId}")
    public ResponseEntity<Void> deleteWordById(@PathVariable UUID wordId) {
        wordsService.deleteWord(wordId);
        return ResponseEntity.noContent().build();
    }
}
