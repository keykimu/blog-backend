package com.example.portfolio.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthRequest {
    @NotBlank(message = "ユーザー名は必須です")
    @Size(max = 255, message = "ユーザー名は255文字以内で入力してください")
    private String username;

    @NotBlank(message = "パスワードは必須です")
    @Size(max = 255, message = "パスワードは255文字以内で入力してください")
    private String password;
}
