package com.example.portfolio.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private String username;
    private LocalDateTime lastLoginAt;
}
