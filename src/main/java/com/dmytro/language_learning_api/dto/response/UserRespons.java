package com.dmytro.language_learning_api.dto.response;

import com.dmytro.language_learning_api.dto.TranslationDTO;
import com.dmytro.language_learning_api.dto.UsersDTO;
import lombok.Data;

import java.util.List;

@Data
public class UserRespons {
    private List<UsersDTO> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
