package com.dmytro.language_learning_api.dto.response;

import com.dmytro.language_learning_api.dto.WordsDTO;
import lombok.Data;

import java.util.List;

@Data
public class WordRespons {
    private List<WordsDTO> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
