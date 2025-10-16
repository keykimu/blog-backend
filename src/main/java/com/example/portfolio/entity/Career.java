package com.example.portfolio.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Career {
    private Long id;
    private Long userId;
    private String year;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
