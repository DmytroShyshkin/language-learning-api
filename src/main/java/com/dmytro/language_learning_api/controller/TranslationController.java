package com.dmytro.language_learning_api.controller;

import com.dmytro.language_learning_api.dto.TranslationDTO;
import com.dmytro.language_learning_api.service.TranslationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/words/{wordsId}/translations")
@RequiredArgsConstructor
@Validated
public class TranslationController {

    private final TranslationService translationService;

    @PostMapping
    public ResponseEntity<TranslationDTO> addTranslation(@PathVariable UUID wordsId, @Valid @RequestBody TranslationDTO translationDto) {
        return ResponseEntity.ok(translationService.addTranslation(wordsId, translationDto));
    }

    @GetMapping("/{translationId}")
    public ResponseEntity<TranslationDTO> getTranslation(@PathVariable UUID translationId){
        return ResponseEntity.ok(translationService.getTranslationById(translationId));
    }

    @PutMapping("/{translationId}")
    public ResponseEntity<TranslationDTO> updateTranslation(@PathVariable UUID translationId, @Valid @RequestBody TranslationDTO translationDto){
        return ResponseEntity.ok(translationService.updateTranslation(translationId, translationDto));
    }

    @DeleteMapping("/{translationId}")
    public void deleteTranslation(@PathVariable UUID translationId){
        translationService.deleteTranslation(translationId);
        ResponseEntity.noContent().build();
    }
}
