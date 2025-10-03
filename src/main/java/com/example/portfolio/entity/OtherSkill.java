package com.example.portfolio.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OtherSkill {
    private Long id;
    private String name;
    private String level;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
