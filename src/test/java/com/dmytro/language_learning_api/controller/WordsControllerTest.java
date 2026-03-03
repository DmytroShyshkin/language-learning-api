package com.dmytro.language_learning_api.controller;

import com.dmytro.language_learning_api.dto.TranslationDTO;
import com.dmytro.language_learning_api.dto.UpdateWordRequest;
import com.dmytro.language_learning_api.dto.WordsDTO;
import com.dmytro.language_learning_api.service.WordsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WordsController.class)
public class WordsControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private WordsService wordsService;

    // GET /{id}
    @Test
    void shouldReturnWordById() throws Exception {
        UUID wordId = UUID.randomUUID();
        WordsDTO wordDto = new WordsDTO(
                wordId,
                "EN",
                "word",
                UUID.randomUUID()
        );

        when(wordsService.getWordById(wordId))
                .thenReturn(wordDto);

        mockMvc.perform(get("/words/{wordId}", wordId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sourceLanguage").value("EN"))
                .andExpect(jsonPath("$.originalWord").value("word"));

        verify(wordsService).getWordById(wordId);
    }

    @Test
    void shouldCreateWord() throws Exception {
        WordsDTO wordDto = new WordsDTO(
                UUID.randomUUID(),
                "en",
                "word",
                UUID.randomUUID()
        );

        when(wordsService.createWord(any()))
                .thenReturn(wordDto);

        mockMvc.perform(post("/words")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "sourceLanguage": "en",
                        "originalWord": "word",
                        "ownerId": "%s"
                    }
                    """.formatted(wordDto.ownerId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.sourceLanguage").value("en"))
                .andExpect(jsonPath("$.originalWord").value("word"));

        verify(wordsService).createWord(any());
    }

    @Test
    void shouldReturnAllWordsByUserId() throws Exception {

        UUID userId = UUID.randomUUID();

        List<WordsDTO> words = List.of(
                new WordsDTO(
                        UUID.randomUUID(),
                        "en",
                        "hello",
                        userId
                ),
                new WordsDTO(
                        UUID.randomUUID(),
                        "en",
                        "world",
                        userId
                )
        );

        when(wordsService.getAllWordsByOwnerId(userId))
                .thenReturn(words);

        mockMvc.perform(get("/words/user/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].originalWord").value("hello"))
                .andExpect(jsonPath("$[1].originalWord").value("world"));

        verify(wordsService).getAllWordsByOwnerId(userId);
    }

    @Test
    void shouldReturnUpdatedWord() throws Exception {
        UUID wordId = UUID.randomUUID();

        WordsDTO wordDto = new WordsDTO(
                wordId,
                "ua",
                "newWord",
                UUID.randomUUID()
        );

        UpdateWordRequest request =
                new UpdateWordRequest("ua", "newWord");

        when(wordsService.updateWord(eq(wordId), any(UpdateWordRequest.class)))
                .thenReturn(wordDto);

        mockMvc.perform(put("/words/update-word/{wordId}", wordId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "sourceLanguage": "ua",
                                "originalWord": "newWord"
                            }
                            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sourceLanguage").value("ua"))
                .andExpect(jsonPath("$.originalWord").value("newWord"));

        verify(wordsService)
                .updateWord(eq(wordId), any(UpdateWordRequest.class));
    }

    @Test
    void shouldReturnWordWithAddedTranslation() throws Exception {
        UUID wordId = UUID.randomUUID();

        WordsDTO wordDto = new WordsDTO(
                wordId,
                "ua",
                "text",
                UUID.randomUUID()
        );

        when(wordsService.addTranslationToWord(eq(wordId), any(TranslationDTO.class)))
                .thenReturn(wordDto);

        mockMvc.perform(put("/words/add-translation-to-word/{wordId}", wordId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "targetLanguage": "ua",
                                "translatedWord": "translationText"
                            }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.sourceLanguage").value("ua"))
                .andExpect(jsonPath("$.originalWord").value("text"));

        verify(wordsService)
                .addTranslationToWord(eq(wordId), any(TranslationDTO.class));
    }

    @Test
    void shouldReturn400WhenTargetLanguageIsBlank() throws Exception {
        UUID wordId = UUID.randomUUID();

        mockMvc.perform(put("/words/add-translation-to-word/{wordId}", wordId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "targetLanguage": "",
                            "translatedWord": "translationText"
                        }
                        """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("targetLanguage"));
    }

    @Test
    void shouldDeleteWordAndReturnNoContent() throws Exception {
        UUID wordId = UUID.randomUUID();

        mockMvc.perform(delete("/words/delete-word/{wordId}", wordId))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(wordsService).deleteWord(eq(wordId));
    }
}
