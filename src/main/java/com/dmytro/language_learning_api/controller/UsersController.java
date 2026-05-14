package com.dmytro.language_learning_api.controller;

import com.dmytro.language_learning_api.dto.UsersDTO;
import com.dmytro.language_learning_api.dto.requests.getRequests.GetUserDataDTO;
import com.dmytro.language_learning_api.dto.response.PageResponse;
import com.dmytro.language_learning_api.dto.requests.updateRequests.UpdatePasswordRequestDTO;
import com.dmytro.language_learning_api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/me")
    public ResponseEntity<GetUserDataDTO> getUserByEmail(
            Authentication authentication
    ) {
        return ResponseEntity.ok(userService.getUserByEmail(authentication.getName()));
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
            , Authentication authentication)
    {
        return ResponseEntity.ok(userService.updateEmail(authentication.getName(), newEmail));
    }

    @PutMapping("/me/username")
    public ResponseEntity<UsersDTO> updateUsername(
            @RequestBody String newUsername
            , Authentication authentication)
    {
        return ResponseEntity.ok(userService.updateUsernameByEmail(authentication.getName(), newUsername));
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> updatePassword(
            @RequestBody UpdatePasswordRequestDTO passwordRequest
            , Authentication authentication)
    {
        userService.updatePasswordByEmail(
                authentication.getName()
                , passwordRequest.oldPassword()
                , passwordRequest.newPassword()
        );

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUser(Authentication authentication) {
        userService.deleteUserByEmail(authentication.getName());

        return ResponseEntity.noContent().build();
    }

}
