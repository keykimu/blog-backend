package com.example.portfolio.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Profile {
    private Long id;
    private Long userId;
    private String name;
    private String nickname;
    private String  nameEn;
    private String intro;
    private String bio;
    private String mail;
    private String github;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
