package com.example.portfolio.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WorkResponse {
    private Long id;
    private String title;
    private String description;
    private String techStack;
    private String url;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
