package com.dmytro.language_learning_api.service;

import com.dmytro.language_learning_api.dto.UsersDTO;
import com.dmytro.language_learning_api.dto.response.UserRespons;
import com.dmytro.language_learning_api.exception.ConflictException.EmailAlreadyExistsException;
import com.dmytro.language_learning_api.exception.ConflictException.UsernameAlreadyExistsException;
import com.dmytro.language_learning_api.exception.NotFoundException.UserNotFoundException;
import com.dmytro.language_learning_api.mapper.UsersMapper;
import com.dmytro.language_learning_api.model.Users;
import com.dmytro.language_learning_api.repository.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;

    @Override
    public UserRespons getAllUsers(int pageNo, int pageSize) {
    //public List<UsersDTO> getAllUsers() {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Users> usersPage = usersRepository.findAll(pageable);
        List<Users> users = usersPage.getContent();
        List<UsersDTO>usersList = users.stream().map(usersMapper::toDto).toList();

        UserRespons userRespons = new UserRespons();
        userRespons.setContent(usersList);
        userRespons.setPageNo(usersPage.getNumber());
        userRespons.setPageSize(usersPage.getSize());
        userRespons.setTotalPages(usersPage.getTotalPages());
        userRespons.setTotalElements(usersPage.getTotalElements());
        userRespons.setLast(usersPage.isLast());

        return userRespons;
    }

    @Override
    public UsersDTO getUserById(UUID userId) {
        Users user = getUserOrThrow(userId);

        return usersMapper.toDto(user);
    }

    @Override
    public UsersDTO createUser(UsersDTO dto) {

        if (usersRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyExistsException(dto.email());
        }

        if (usersRepository.existsByUsername(dto.username())) {
            throw new UsernameAlreadyExistsException(dto.username());
        }

        Users user = usersMapper.fromDto(dto);

        Users savedUser = usersRepository.save(user);

        return usersMapper.toDto(savedUser);
    }

    @Override
    public UsersDTO updateEmail(UUID userId, String newEmail) {
        Users user = getUserOrThrow(userId);

        if (usersRepository.existsByEmail(newEmail)) {
            throw new EmailAlreadyExistsException("Email: " + newEmail + " already in use");
        }

        user.setEmail(newEmail);
        usersRepository.save(user);

        return usersMapper.toDto(user);
    }

    @Override
    public UsersDTO updateUsername(UUID userId, String newUsername) {
        Users user = getUserOrThrow(userId);

        if (usersRepository.existsByUsername(newUsername)) {
            throw new UsernameAlreadyExistsException("Username: " + newUsername + " already in use");
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
