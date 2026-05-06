package com.dmytro.language_learning_api.controller;

import com.dmytro.language_learning_api.dto.UsersDTO;
import com.dmytro.language_learning_api.dto.response.PageResponse;
import com.dmytro.language_learning_api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UsersController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<PageResponse<UsersDTO>> getAllUsers(
            @RequestParam(defaultValue = "0", required = false)int pageNo,
            @RequestParam(defaultValue = "10", required = false)int pageSize
    ) {
        return new ResponseEntity<>(userService.getAllUsers(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping
    public ResponseEntity<UsersDTO> createUser(@Valid @RequestBody UsersDTO userDTO) {
        UsersDTO createdUser = userService.createUser(userDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUser);
    }

    @PutMapping("/me/email")
    public ResponseEntity<UsersDTO> updateEmail(
            @RequestBody String newEmail
            , Authentication authentication) {
        return ResponseEntity.ok(userService.updateEmail(authentication.getName(), newEmail));
    }

    @PutMapping("/me/username")
    public ResponseEntity<UsersDTO> updateUsername(
            @RequestBody String newUsername
            , Authentication authentication) {
        return ResponseEntity.ok(userService.updateUsernameByEmail(authentication.getName(), newUsername));
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> updatePassword(
            @PathVariable String oldPassword
            , @Valid @RequestParam String newPassword
            , Authentication authentication) {
        userService.updatePasswordByEmail(authentication.getName(), oldPassword, newPassword);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUser(Authentication authentication) {
        String email = authentication.getName();
        userService.deleteUserByEmail(email);
        return ResponseEntity.noContent().build();
    }

}
