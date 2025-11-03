package com.example.portfolio.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProfileUpdateRequest {
    @NotBlank(message = "名前は必須です")
    @Size(max = 255, message = "名前は255文字以内で入力してください")
    private String name;

    @NotBlank(message = "ニックネームは必須です")
    @Size(max = 255, message = "ニックネームは255文字以内で入力してください")
    private String nickname;

    @NotBlank(message = "名前(英語)は必須です")
    @Size(max = 255, message = "名前(英語)は255文字以内で入力してください")
    private String nameEn;

    @NotBlank(message = "一言は必須です")
    @Size(max = 255, message = "一言は255文字以内で入力してください")
    private String intro;

    @NotBlank(message = "自己紹介は必須です")
    @Size(max = 500, message = "自己紹介は500文字以内で入力してください")
    private String bio;

    @NotBlank(message = "アイコン画像は必須です")
    @Size(max = 500, message = "アイコン画像は255文字以内で入力してください")
    private String imageName;

    @NotBlank(message = "メールは必須です")
    @Size(max = 255, message = "メールは255文字以内で入力してください")
    @Email(message = "メールは正しい形式で入力してください")
    private String mail;

    @NotBlank(message = "githubURLは必須です")
    @Size(max = 255, message = "githubURLは255文字以内で入力してください")
    private String github;
}
