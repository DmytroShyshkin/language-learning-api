package com.dmytro.language_learning_api.service;

import com.dmytro.language_learning_api.dto.UsersDTO;
import com.dmytro.language_learning_api.exception.ConflictException.EmailAlreadyExistsException;
import com.dmytro.language_learning_api.exception.NotFoundException.UserNotFoundException;
import com.dmytro.language_learning_api.mapper.UsersMapper;
import com.dmytro.language_learning_api.model.Users;
import com.dmytro.language_learning_api.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {
    @Mock
    private UsersRepository usersRepository;
    @Mock
    private UsersMapper usersMapper;

    @InjectMocks
    private UserServiceImpl usersService;

    // Test of error 404. User does not exist
    @Test
    void shouldThrowUserNotFoundExceptionWhenUserDoesNotExist() {

        UUID id = UUID.randomUUID();

        when(usersRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> usersService.getUserById(id));
    }

    @Test
    void shouldReturnCreatedUser(){
        UsersDTO dto = new UsersDTO(
                null,
                "test@test.com",
                "password123",
                "username"
        );

        Users user = new Users();
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setUsername(dto.username());

        when(usersRepository.existsByEmail(dto.email()))
                .thenReturn(false);

        when(usersMapper.fromDto(dto))
                .thenReturn(user);

        when(usersRepository.save(user))
                .thenReturn(user);

        usersService.createUser(dto);

        verify(usersRepository).save(user);
        verify(usersRepository).existsByEmail(dto.email());
        verify(usersMapper).fromDto(dto);
    }

    @Test
    void shouldReturnUserWhenUserExists() {

        UUID id = UUID.randomUUID();

        Users user = new Users();
        user.setId(id);
        user.setEmail("test@test.com");

        UsersDTO dto = new UsersDTO(
                id,
                "test@test.com",
                "password123",
                "username"
        );

        when(usersRepository.findById(id))
                .thenReturn(Optional.of(user));

        when(usersMapper.toDto(user))
                .thenReturn(dto);

        UsersDTO result = usersService.getUserById(id);

        assertEquals("test@test.com", result.email());
    }

    // Test of error 409. Email already exists
    @Test
    void shouldThrowEmailAlreadyExistsExceptionWhenEmailExists() {

        UsersDTO dto = new UsersDTO(
                null,
                "test@test.com",
                "password123",
                "username"
        );

        when(usersRepository.existsByEmail(dto.email()))
                .thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class,
                () -> usersService.createUser(dto));
    }
}
