package com.example.portfolio.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FrameworkResponse {
    private Long id;
    private String name;
    private String level;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
