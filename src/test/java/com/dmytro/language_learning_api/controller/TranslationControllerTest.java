package com.dmytro.language_learning_api.controller;

import com.dmytro.language_learning_api.dto.TranslationDTO;
import com.dmytro.language_learning_api.service.TranslationService;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TranslationController.class)
public class TranslationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private TranslationService translationService;

    @Test
    void shouldAddTranslationToWord() throws Exception {
        UUID wordId = UUID.randomUUID();

        TranslationDTO responseDto = new TranslationDTO(
                UUID.randomUUID(),
                "ua",
                "translationText",
                "syn"
                //new ArrayList<>()
        );

        when(translationService.addTranslation(eq(wordId), any(TranslationDTO.class)))
                .thenReturn(responseDto);

        mockMvc.perform(post("/words/{wordsId}/translations", wordId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "targetLanguage": "ua",
                            "translatedWord": "translationText"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.targetLanguage").value("ua"))
                .andExpect(jsonPath("$.translatedWord").value("translationText"))
                .andExpect(jsonPath("$.description").value("syn"));

        verify(translationService)
                .addTranslation(eq(wordId), any(TranslationDTO.class));
    }

    @Test
    void shouldReturnCreatedTranslation() throws Exception {
        UUID wordId = UUID.randomUUID();
        UUID translationId = UUID.randomUUID();

        TranslationDTO TranslationDto = new TranslationDTO(
                translationId,
                "ua",
                "translationText",
                "syn"
                //new ArrayList<>()
        );

        when(translationService.getTranslationById(translationId))
                .thenReturn(TranslationDto);

        mockMvc.perform(get("/words/{wordsId}/translations/{translationId}", wordId, translationId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "targetLanguage": "ua",
                            "translatedWord": "translationText"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.targetLanguage").value("ua"))
                .andExpect(jsonPath("$.translatedWord").value("translationText"))
                .andExpect(jsonPath("$.description").value("syn"));

        verify(translationService)
                .getTranslationById(eq(translationId));
    }

    @Test
    void shouldUpdateTranslation() throws Exception {
        UUID wordId = UUID.randomUUID();
        UUID translationId = UUID.randomUUID();

        TranslationDTO translationDto = new TranslationDTO(
                translationId,
                "ua",
                "translationText",
                "syn"
                //new ArrayList<>()
        );

        when(translationService.updateTranslation(eq(translationId), any(TranslationDTO.class)))
                .thenReturn(translationDto);

        mockMvc.perform(put("/words/{wordsId}/translations/{translationId}", wordId, translationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "targetLanguage": "ua",
                        "translatedWord": "translationText"
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(translationId.toString()))
                .andExpect(jsonPath("$.targetLanguage").value("ua"))
                .andExpect(jsonPath("$.translatedWord").value("translationText"))
                .andExpect(jsonPath("$.description").value("syn"));

        verify(translationService)
                .updateTranslation(eq(translationId), any(TranslationDTO.class));
    }

    @Test
    void shouldDeleteTranslation() throws Exception {

        UUID wordId = UUID.randomUUID();
        UUID translationId = UUID.randomUUID();

        mockMvc.perform(delete("/words/{wordsId}/translations/{translationId}",
                        wordId, translationId))
                .andExpect(status().isNoContent());

        verify(translationService)
                .deleteTranslation(translationId);
    }

}
