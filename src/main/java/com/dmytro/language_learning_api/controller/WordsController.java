package com.dmytro.language_learning_api.controller;

import com.dmytro.language_learning_api.dto.TranslationDTO;
import com.dmytro.language_learning_api.dto.UpdateWordRequest;
import com.dmytro.language_learning_api.dto.WordsDTO;
import com.dmytro.language_learning_api.service.WordsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/words")
@AllArgsConstructor
@Validated
public class WordsController {

    public final WordsService wordsService;

    @GetMapping("/{wordId}")
    public ResponseEntity<WordsDTO> getWordsById(@PathVariable UUID wordId) {
        return ResponseEntity.ok(wordsService.getWordById(wordId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WordsDTO>> getAllWordsByOwnerId(@PathVariable UUID userId) {
        return ResponseEntity.ok(wordsService.getAllWordsByOwnerId(userId));
    }

    @PostMapping
    public ResponseEntity<WordsDTO> createWord(@Valid @RequestBody WordsDTO wordsDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(wordsService.createWord(wordsDto));
    }

    @PutMapping("/update-word/{wordId}")
    public ResponseEntity<WordsDTO> updateOriginalWordById(@PathVariable UUID wordId, @RequestBody UpdateWordRequest updateWordRequest) {
        return ResponseEntity.ok(wordsService.updateWord(wordId, updateWordRequest));
    }

    @PutMapping("/add-translation-to-word/{wordId}")
    public ResponseEntity<WordsDTO> addTranslationToWordById(@PathVariable UUID wordId, @Valid @RequestBody TranslationDTO translationDto) {
        return  ResponseEntity.ok(wordsService.addTranslationToWord(wordId, translationDto));
    }

    @DeleteMapping("/delete-word/{wordId}")
    public ResponseEntity<Void> deleteWordById(@PathVariable UUID wordId) {
        wordsService.deleteWord(wordId);
        return ResponseEntity.noContent().build();
    }
}
