package com.dmytro.language_learning_api.service.securityService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CookieService {
    void addRefreshToken(HttpServletResponse response, String refreshToken);
    String extractRefreshToken(HttpServletRequest request);
}
