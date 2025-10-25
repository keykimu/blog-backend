package com.example.portfolio.service.admin;

import com.example.portfolio.request.AuthRequest;
import com.example.portfolio.response.admin.AuthResponse;
import com.example.portfolio.response.admin.UserResponse;
import com.example.portfolio.service.common.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminAuthService {
    private final AuthService authService;
    public List<UserResponse> getAllAdmins() {
        return authService.getAllAdmins();
    }

    public AuthResponse login(AuthRequest request) {
        return authService.login(request);
    }
}
