package com.example.portfolio.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Framework {
    private Long id;
    private Long userId;
    private String name;
    private String level;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
