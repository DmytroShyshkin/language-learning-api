package com.dmytro.language_learning_api.controller;

import com.dmytro.language_learning_api.dto.TranslationDTO;
import com.dmytro.language_learning_api.service.TranslationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/words/{wordId}/translations")
@RequiredArgsConstructor
@Validated
public class TranslationController {

    private final TranslationService translationService;

    @GetMapping
    public ResponseEntity<List<TranslationDTO>> getTranslations(@PathVariable UUID wordId) {
        return ResponseEntity.ok(translationService.getTranslationsByWordId(wordId));
    }

    @GetMapping("/{translationId}")
    public ResponseEntity<TranslationDTO> getTranslation(@PathVariable UUID translationId){
        return ResponseEntity.ok(translationService.getTranslationById(translationId));
    }

    @PostMapping
    public ResponseEntity<TranslationDTO> addTranslation(@PathVariable UUID wordId, @Valid @RequestBody TranslationDTO translationDto) {
        return ResponseEntity.ok(translationService.addTranslation(wordId, translationDto));
    }

    @PutMapping("/{translationId}")
    public ResponseEntity<TranslationDTO> updateTranslation(@PathVariable UUID translationId, @Valid @RequestBody TranslationDTO translationDto){
        return ResponseEntity.ok(translationService.updateTranslation(translationId, translationDto));
    }

    @DeleteMapping("/{translationId}")
    public ResponseEntity<Void> deleteTranslation(@PathVariable UUID translationId){
        translationService.deleteTranslation(translationId);
        return ResponseEntity.noContent().build();
    }
}
