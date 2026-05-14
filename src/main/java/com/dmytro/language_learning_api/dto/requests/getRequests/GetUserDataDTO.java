package com.dmytro.language_learning_api.dto.requests.getRequests;

public record GetUserDataDTO(
        String username
        , String email
) {
}
