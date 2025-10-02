package com.example.portfolio.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LanguageResponse {
    private Long id;
    private String name;
    private String level;
    private String experience;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
