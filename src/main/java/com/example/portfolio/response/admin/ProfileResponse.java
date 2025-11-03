package com.example.portfolio.response.admin;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProfileResponse {
    private Long id;
    private String name;
    private String nickname;
    private String nameEn;
    private String intro;
    private String bio;
    private String imageName;
    private String mail;
    private String github;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
