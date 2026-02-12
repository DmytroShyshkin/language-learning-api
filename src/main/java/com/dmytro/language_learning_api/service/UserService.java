package com.dmytro.language_learning_api.service;

import com.dmytro.language_learning_api.dto.UsersDTO;

import java.util.UUID;

public interface UserService {

    UsersDTO getUserById(UUID userId);

    UsersDTO createUser(UsersDTO dto);

    UsersDTO updateEmail(UUID userId, String newEmail);

    UsersDTO updateUsername(UUID userId, String newUsername);

    void updatePassword(UUID userId, String newPassword);

    void deleteUser(UUID userId);

}
