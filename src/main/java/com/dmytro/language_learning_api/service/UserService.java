package com.dmytro.language_learning_api.service;

import com.dmytro.language_learning_api.dto.UsersDTO;
import com.dmytro.language_learning_api.dto.response.UserRespons;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserRespons getAllUsers(int pageNo, int pageSize);
    //List<UsersDTO> getAllUsers(int pageNo, int pageSize);
    UsersDTO getUserById(UUID userId);

    UsersDTO createUser(UsersDTO dto);

    UsersDTO updateEmail(UUID userId, String newEmail);

    UsersDTO updateUsername(UUID userId, String newUsername);

    void updatePassword(UUID userId, String newPassword);

    void deleteUser(UUID userId);

}
