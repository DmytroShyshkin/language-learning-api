package com.dmytro.language_learning_api.controller;

import com.dmytro.language_learning_api.dto.UsersDTO;
import com.dmytro.language_learning_api.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsersController.class)
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private UserService userService;
    private final UUID id = UUID.randomUUID();

    // GET /{id}
    @Test
    void shouldReturnUserById() throws Exception {

        UsersDTO dto = new UsersDTO(
                id,
                "test@test.com",
                "pass",
                "username"
        );

        when(userService.getUserById(id))
                .thenReturn(dto);

        mockMvc.perform(get("/users/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andExpect(jsonPath("$.username").value("username"));

        verify(userService).getUserById(id);
    }

    // PUT / (createUser)
    @Test
    void shouldCreateUser() throws Exception {

        UsersDTO request = new UsersDTO(
                null,
                "new@test.com",
                "password123",
                "newUser"
        );

        UsersDTO response = new UsersDTO(
                id,
                "new@test.com",
                "password123",
                "newUser"
        );

        when(userService.createUser(any()))
                .thenReturn(response);

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                            "email": "new@test.com",
                            "password": "password123",
                            "username": "newUser"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value("new@test.com"));

        verify(userService).createUser(any());
    }

    // PUT /{id}/email
    @Test
    void shouldUpdateEmail() throws Exception {

        UsersDTO dto = new UsersDTO(
                id,
                "updated@test.com",
                "password123",
                "username"
        );

        when(userService.updateEmail(id, "updated@test.com"))
                .thenReturn(dto);

        mockMvc.perform(put("/users/" + id + "/email")
                        .param("email", "updated@test.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("updated@test.com"));

        verify(userService).updateEmail(id, "updated@test.com");
    }

    // PUT /{id}/username
    @Test
    void shouldUpdateUsername() throws Exception {

        UsersDTO dto = new UsersDTO(
                id,
                "test@test.com",
                "password123",
                "newUsername"
        );

        when(userService.updateUsername(id, "newUsername"))
                .thenReturn(dto);

        mockMvc.perform(put("/users/" + id + "/username")
                        .param("username", "newUsername"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("newUsername"));

        verify(userService).updateUsername(id, "newUsername");
    }

    // PUT /{id}/password
    @Test
    void shouldUpdatePassword() throws Exception {

        doNothing().when(userService).updatePassword(id, "newPassword");

        mockMvc.perform(put("/users/" + id + "/password")
                        .param("password", "newPassword"))
                .andExpect(status().isNoContent());

        verify(userService).updatePassword(id, "newPassword");
    }

    // Validation test
    @Test
    void shouldReturn400WhenPasswordTooShort() throws Exception {

        mockMvc.perform(put("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "email": "new@test.com",
                        "password": "pass",
                        "username": "newUser"
                    }
                    """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("password"));
    }
}
