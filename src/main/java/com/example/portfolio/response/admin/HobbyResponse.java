package com.example.portfolio.response.admin;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HobbyResponse {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
