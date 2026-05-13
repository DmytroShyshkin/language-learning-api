package com.dmytro.language_learning_api.dto.updateRequests;

public record UpdatePasswordRequest(
        String oldPassword
        , String newPassword
) {
}
