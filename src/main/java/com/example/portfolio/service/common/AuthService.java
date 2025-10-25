package com.example.portfolio.service.common;

import com.example.portfolio.entity.User;
import com.example.portfolio.exception.AuthFailedException;
import com.example.portfolio.exception.BadRequestException;
import com.example.portfolio.mapper.UserMapper;
import com.example.portfolio.mapstruct.UserEntityMapper;
import com.example.portfolio.request.AuthRequest;
import com.example.portfolio.response.admin.AuthResponse;
import com.example.portfolio.response.admin.UserResponse;
import com.example.portfolio.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserMapper userMapper;
    private final UserEntityMapper userEntityMapper;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<UserResponse> getAllAdmins() {
        List<User> users = userMapper.findAll();
        return userEntityMapper.toResponseList(users);
    }

    public AuthResponse login(AuthRequest request) {
        if (request.getUsername() == null || request.getUsername().isEmpty() ||
                request.getPassword() == null || request.getPassword().isEmpty()) {
            throw new BadRequestException("ユーザー名とパスワードは必須です");
        }

        User user = userMapper.findByUsername(request.getUsername());

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AuthFailedException("認証に失敗しました");
        }

        // last_login_at を更新
        userMapper.updateLastLogin(user.getId(), LocalDateTime.now());

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        return new AuthResponse(token);
    }

}
