package com.example.portfolio.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCheckResponse {
    private Long userId;
    private String username;
}
