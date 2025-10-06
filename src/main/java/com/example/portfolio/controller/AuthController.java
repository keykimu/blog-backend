package com.example.portfolio.controller;

import com.example.portfolio.request.AuthRequest;
import com.example.portfolio.response.AuthCheckResponse;
import com.example.portfolio.response.AuthResponse;
import com.example.portfolio.response.UserResponse;
import com.example.portfolio.service.AuthService;
import com.example.portfolio.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @GetMapping("/admins")
    public ResponseEntity<List<UserResponse>> getAdmins() {
        return ResponseEntity.ok(authService.getAllAdmins());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/check")
    public ResponseEntity<AuthCheckResponse> check(@RequestHeader("Authorization") String token)  {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return ResponseEntity.ok(jwtUtil.validateToken(token));
    }
}
