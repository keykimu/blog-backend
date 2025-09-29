package com.example.portfolio.request;

import lombok.Data;

@Data
public class ProfileUpdateRequest {
    private String name;
    private String nickname;
    private String nameEn;
    private String intro;
    private String bio;
    private String mail;
    private String github;
}
