package com.example.portfolio.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Language {
    private Long id;
    private String name;
    private String level;
    private String experience;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
