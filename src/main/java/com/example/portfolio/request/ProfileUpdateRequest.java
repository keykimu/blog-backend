package com.example.portfolio.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProfileUpdateRequest {
    @NotBlank(message = "名前は必須です")
    private String name;
    @NotBlank(message = "ニックネームは必須です")
    private String nickname;
    @NotBlank(message = "名前(英語)は必須です")
    private String nameEn;
    @NotBlank(message = "一言は必須です")
    private String intro;
    @NotBlank(message = "自己紹介は必須です")
    private String bio;
    @Email(message = "メールは正しい形式で入力してください")
    private String mail;
    @NotBlank(message = "githubは必須です")
    private String github;
}
