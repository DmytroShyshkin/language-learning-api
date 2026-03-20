package com.dmytro.language_learning_api.controller;

import com.dmytro.language_learning_api.dto.UsersDTO;
import com.dmytro.language_learning_api.model.Role;
import com.dmytro.language_learning_api.model.Users;
import com.dmytro.language_learning_api.repository.UsersRepository;
import com.dmytro.language_learning_api.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String register(@RequestBody UsersDTO dto) {

        Users user = Users.builder()
                .email(dto.email())
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .role(Role.USER)
                .build();

        usersRepository.save(user);

        return "User created";
    }

    @PostMapping("/login")
    public String login(@RequestBody UsersDTO dto) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.email(),
                        dto.password()
                )
        );

        return jwtService.generateToken(dto.email());
    }
}
