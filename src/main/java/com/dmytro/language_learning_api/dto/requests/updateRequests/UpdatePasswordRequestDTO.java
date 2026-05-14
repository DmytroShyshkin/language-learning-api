package com.dmytro.language_learning_api.dto.requests.updateRequests;

public record UpdatePasswordRequestDTO(
        String oldPassword
        , String newPassword
) {
}
