package com.dmytro.language_learning_api.service;

import com.dmytro.language_learning_api.dto.UsersDTO;
import com.dmytro.language_learning_api.exception.ConflictException.EmailAlreadyExistsException;
import com.dmytro.language_learning_api.exception.ConflictException.UsernameAlreadyExistsException;
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
import static org.mockito.Mockito.*;

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

        verify(usersRepository).findById(id);
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

        Users savedUser = new Users();
        savedUser.setId(UUID.randomUUID());
        savedUser.setEmail(dto.email());
        savedUser.setPassword(dto.password());
        savedUser.setUsername(dto.username());

        when(usersRepository.existsByEmail(dto.email()))
                .thenReturn(false);

        when(usersRepository.existsByUsername(dto.username()))
                .thenReturn(false);

        when(usersMapper.fromDto(dto))
                .thenReturn(user);

        when(usersRepository.save(user))
                .thenReturn(savedUser);

        when(usersMapper.toDto(savedUser))
                .thenReturn(
                        new UsersDTO(
                                savedUser.getId(),
                                savedUser.getEmail(),
                                savedUser.getPassword(),
                                savedUser.getUsername()
                        )
                );

        UsersDTO result = usersService.createUser(dto);

        assertEquals(dto.email(), result.email());
        assertEquals(dto.username(), result.username());

        verify(usersRepository).save(user);
    }

    // Test of error 409. Username already exists
    @Test
    void shouldThrowUsernameAlreadyExistsException() {

        UsersDTO dto = new UsersDTO(
                null,
                "test@test.com",
                "pass",
                "username"
        );

        when(usersRepository.existsByEmail(dto.email()))
                .thenReturn(false);

        when(usersRepository.existsByUsername(dto.username()))
                .thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class,
                () -> usersService.createUser(dto));

        verify(usersRepository, never()).save(any());
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

    @Test
    void shouldCreateUserSuccessfully() {

        UsersDTO dto = new UsersDTO(
                null,
                "test@test.com",
                "pass",
                "username"
        );

        Users user = new Users();
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setUsername(dto.username());

        Users savedUser = new Users();
        savedUser.setId(UUID.randomUUID());
        savedUser.setEmail(dto.email());
        savedUser.setPassword(dto.password());
        savedUser.setUsername(dto.username());

        when(usersRepository.existsByEmail(dto.email()))
                .thenReturn(false);

        when(usersRepository.existsByUsername(dto.username()))
                .thenReturn(false);

        when(usersMapper.fromDto(dto))
                .thenReturn(user);

        when(usersRepository.save(any(Users.class)))
                .thenReturn(savedUser);

        when(usersMapper.toDto(savedUser))
                .thenReturn(
                        new UsersDTO(
                                savedUser.getId(),
                                savedUser.getEmail(),
                                savedUser.getPassword(),
                                savedUser.getUsername()
                        )
                );

        UsersDTO result = usersService.createUser(dto);

        assertEquals(dto.email(), result.email());
        assertEquals(dto.username(), result.username());

        verify(usersRepository).save(any(Users.class));
    }

}
