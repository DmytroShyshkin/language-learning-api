package com.dmytro.language_learning_api.service;

import com.dmytro.language_learning_api.dto.UsersDTO;
import com.dmytro.language_learning_api.dto.response.PageResponse;

import java.util.UUID;

public interface UserService {

    PageResponse<UsersDTO> getAllUsers(int pageNo, int pageSize);
    //List<UsersDTO> getAllUsers(int pageNo, int pageSize);
    UsersDTO getUserById(UUID userId);

    UsersDTO createUser(UsersDTO dto);

    UsersDTO updateEmail(String oldEmail, String newEmail);

    UsersDTO updateUsernameByEmail(String currentEmail, String newUsername);

    void updatePasswordByEmail(String currentEmail,String oldPassword, String newPassword);

    void deleteUserByEmail(String email);

    void deleteUser(UUID userId);

}
