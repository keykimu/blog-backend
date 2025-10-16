package com.example.portfolio.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Work {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private String techStack;
    private String url;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
