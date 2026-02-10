package com.dmytro.language_learning_api.controller;

import com.dmytro.language_learning_api.dto.UsersDTO;
import com.dmytro.language_learning_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PutMapping("/{id}/email")
    public ResponseEntity<UsersDTO> updateEmail(@PathVariable UUID id, @RequestParam String email) {
        return ResponseEntity.ok(userService.updateEmail(id, email));
    }

    @PutMapping("/{id}/username")
    public ResponseEntity<UsersDTO> updateUsername(@PathVariable UUID id, @RequestParam String username) {
        return ResponseEntity.ok(userService.updateUsername(id, username));
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable UUID id, @RequestParam String password) {
        userService.updatePassword(id, password);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
