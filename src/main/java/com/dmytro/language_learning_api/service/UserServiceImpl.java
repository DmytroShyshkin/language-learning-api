package com.dmytro.language_learning_api.service;

import com.dmytro.language_learning_api.dto.UsersDTO;
import com.dmytro.language_learning_api.exception.UserNotFoundException;
import com.dmytro.language_learning_api.mapper.UsersMapper;
import com.dmytro.language_learning_api.model.Users;
import com.dmytro.language_learning_api.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private UsersRepository usersRepository;
    private UsersMapper usersMapper;

    @Override
    public UsersDTO getUserById(UUID userId) {
        Users user = getUserOrThrow(userId);

        return usersMapper.toDto(user);
    }

    @Override
    public UsersDTO updateEmail(UUID userId, String newEmail) {
        Users user = getUserOrThrow(userId);

        if (usersRepository.existsByEmail(newEmail)) {
            throw new RuntimeException("Email already in use");
        }

        user.setEmail(newEmail);
        usersRepository.save(user);

        return usersMapper.toDto(user);
    }

    @Override
    public UsersDTO updateUsername(UUID userId, String newUsername) {
        Users user = getUserOrThrow(userId);

        if (usersRepository.existsByUsername(newUsername)) {
            throw new RuntimeException("Username already in use");
        }

        user.setUsername(newUsername);
        usersRepository.save(user);

        return usersMapper.toDto(user);
    }

    @Override
    public void updatePassword(UUID userId, String newPassword) {
        Users user = getUserOrThrow(userId);

        // luego estaría PasswordEncoder
        user.setPassword(newPassword);
        usersRepository.save(user);
    }

    @Override
    public void deleteUser(UUID userId) {
        Users user = getUserOrThrow(userId);
        usersRepository.delete(user);
    }

    // Clases auxiliares
    private Users getUserOrThrow(UUID userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User User with id " + userId + " not found"));
    }

}
