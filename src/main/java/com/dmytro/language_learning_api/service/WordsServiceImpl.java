package com.dmytro.language_learning_api.service;

import com.dmytro.language_learning_api.dto.TranslationDTO;
import com.dmytro.language_learning_api.dto.WordsDTO;
import com.dmytro.language_learning_api.exception.NotFoundException.UserNotFoundException;
import com.dmytro.language_learning_api.exception.NotFoundException.WordNotFoundException;
import com.dmytro.language_learning_api.mapper.TranslationMapper;
import com.dmytro.language_learning_api.mapper.WordsMapper;
import com.dmytro.language_learning_api.model.Translation;
import com.dmytro.language_learning_api.model.Users;
import com.dmytro.language_learning_api.model.Words;
import com.dmytro.language_learning_api.repository.UsersRepository;
import com.dmytro.language_learning_api.repository.WordsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WordsServiceImpl implements WordsService {

    // Mappers
    final WordsMapper wordsMapper;
    final TranslationMapper translationMapper;
    // Repository
    final WordsRepository wordsRepository;
    final UsersRepository usersRepository;

    @Override
    public WordsDTO createWord(WordsDTO dto) {
        Users owner = usersRepository.findById(dto.ownerId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Words word = wordsMapper.fromDto(dto);
        /*
        word.setSourceLanguage(dto.sourceLanguage());
        word.setOriginalWord(dto.originalWord());
        word.setOwner(owner);
        */
        Words savedWord = wordsRepository.save(word);
        return wordsMapper.toDto(savedWord);
    }

    @Override
    public WordsDTO getWordById(UUID wordId) {
        Words word = getWordOrThrow(wordId);
        return wordsMapper.toDto(word);
    }

    @Override
    public WordsDTO updateSourceLanguage(UUID wordId, String newSourceWord) {
        Words word = getWordOrThrow(wordId);
        word.setSourceLanguage(newSourceWord);

        wordsRepository.save(word);

        //WordsDTO savedWordDto = new WordsMapper().toDto(word);

        return wordsMapper.toDto(word);
    }

    @Override
    public WordsDTO updateOriginalWord(UUID wordId, String newOriginalWord) {
        Words word = getWordOrThrow(wordId);
        word.setOriginalWord(newOriginalWord);

        wordsRepository.save(word);

        return wordsMapper.toDto(word);
    }

    @Override
    public List<WordsDTO> getAllWordsByOwnerId(UUID ownerId) {
        List<Words> words = wordsRepository.findByOwnerId(ownerId);

        return words.stream()
                .map(wordsMapper::toDto)
                .toList();
    }

    @Override
    public WordsDTO addTranslationToWord(UUID wordId, TranslationDTO translationDto) {
        Words word = getWordOrThrow(wordId);

        Translation translation = translationMapper.fromDto(translationDto);
        translation.setWord(word);

        word.getTranslations().add(translation);
        Words savedWord = wordsRepository.save(word);

        return wordsMapper.toDto(savedWord);
    }

    @Override
    public void deleteWord(UUID wordId) {
        Words word = getWordOrThrow(wordId);
        wordsRepository.delete(word);
    }

    // Clases auxiliares
    private List<Words> getWordsByOwnerOrThrow(UUID ownerId) {
        List<Words> words = wordsRepository.findByOwnerId(ownerId);

        if (words.isEmpty()) {
            throw new UserNotFoundException("User with id " + ownerId + " has no words");
        }

        return words;
    }

    private Words getWordOrThrow(UUID wordId) {
        return wordsRepository.findById(wordId)
                .orElseThrow(() -> new WordNotFoundException(
                        "Word with id " + wordId + " not found"
                ));
    }
}
