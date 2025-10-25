package com.example.portfolio.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PublicWorkResponse {
    private Long id;
    private String title;
    private String description;
    private String techStack;
    private String url;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
