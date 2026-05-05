package com.dmytro.language_learning_api.service.securityService;

import com.dmytro.language_learning_api.dto.UsersDTO;
import com.dmytro.language_learning_api.dto.authentication.AuthResponse;
import com.dmytro.language_learning_api.dto.authentication.LoginRequest;
import com.dmytro.language_learning_api.exception.NotFoundException.NotFoundException;
import com.dmytro.language_learning_api.exception.NotFoundException.UserNotFoundException;
import com.dmytro.language_learning_api.model.Role;
import com.dmytro.language_learning_api.model.Users;
import com.dmytro.language_learning_api.repository.UsersRepository;
import com.dmytro.language_learning_api.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public AuthResponse register(UsersDTO dto) {
        String verificationToken = UUID.randomUUID().toString();

        Users user = Users.builder()
                .email(dto.email())
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .role(Role.USER)
                .emailVerified(false)
                .verificationToken(verificationToken)
                .build();

        usersRepository.save(user);

        emailService.sendVerificationEmail(user.getEmail(), verificationToken);

        String accessToken = jwtService.generateAccessToken(user.getEmail(), user.getRole());

        return new AuthResponse(
                user.getId(),
                accessToken,
                null,
                user.getEmail(),
                user.getUsername()
        );
        /*
        Users user = Users.builder()
                .email(dto.email())
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .role(Role.USER)
                .build();

        usersRepository.save(user);

        String accessToken = jwtService.generateAccessToken(user.getEmail(), user.getRole());
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());

        return new AuthResponse(
                user.getId(),
                accessToken,
                null,
                user.getEmail(),
                user.getUsername()
        );
        */
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        Users user = usersRepository.findByEmail(request.email())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String accessToken = jwtService.generateAccessToken(user.getEmail(), user.getRole());

        return new AuthResponse(
                user.getId(),
                accessToken,
                null,
                user.getEmail(),
                user.getUsername()
        );
    }

    @Override
    public void verifyEmail(String token) {
        Users user = usersRepository.findByVerificationToken(token)
                .orElseThrow(() -> new NotFoundException("Invalid or expired token."));

        user.setEmailVerified(true);
        user.setVerificationToken(null);
        usersRepository.save(user);
    }

    @Override
    public AuthResponse refresh(String refreshToken) {
        String email = jwtService.extractEmail(refreshToken);

        if (!jwtService.isValid(refreshToken, email)) {
            throw new RuntimeException("Invalid refresh token");
        }

        Users user = usersRepository.findByEmail(email)
                .orElseThrow();

        String newAccessToken =
                jwtService.generateAccessToken(user.getEmail(), user.getRole());

        return new AuthResponse(
                user.getId(),
                newAccessToken,
                refreshToken,
                user.getEmail(),
                user.getUsername()
        );
    }
}
