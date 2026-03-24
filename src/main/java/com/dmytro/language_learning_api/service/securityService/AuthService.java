package com.dmytro.language_learning_api.service.securityService;

import com.dmytro.language_learning_api.dto.UsersDTO;
import com.dmytro.language_learning_api.dto.authentication.AuthResponse;

public interface AuthService {
    AuthResponse register(UsersDTO dto);
    AuthResponse login(UsersDTO dto);
    AuthResponse refresh(String refreshToken);
}
